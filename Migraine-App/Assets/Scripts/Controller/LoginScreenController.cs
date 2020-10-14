using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Threading.Tasks;
using TMPro;
using UnityEngine;
using UnityEngine.UI;
using clinvest.migraine.Model;
using System.Diagnostics;

namespace clinvest.migraine.Controller
{

    public class LoginScreenController : MonoBehaviour
    {
        private static string serverURL;

        // Login Panel Fields
        public TMP_InputField LoginPanelUsername;
        public TMP_InputField LoginPanelPassword;

        // Registration Panel Fields
        public TMP_InputField RegPanelFirstName;
        public TMP_InputField RegPanelLastName;
        public TMP_Dropdown RegPanelBirthMonth;
        public TMP_Dropdown RegPanelBirthYear;
        public TMP_Dropdown RegPanelSex;
        public TMP_InputField RegPanelEmail;
        public TMP_InputField RegPanelPassword;

        // Reset Panel Fields
        public TMP_InputField ResetPanelEmail;

        // Panels
        public GameObject LoginPanel;
        public GameObject RegistrationPanel;
        public GameObject PasswordResetPanel;
        public GameObject HomePanel;


        // Start is called before the first frame update
        void Start()
        {
            if (PlayerPrefs.HasKey("server_url"))
            {
                serverURL = PlayerPrefs.GetString("server_url");
            }
            else
            {
                serverURL = "https://app.clinvest.com/migraine-server";
            }

            ServicePointManager.ServerCertificateValidationCallback = delegate (
            System.Object obj, X509Certificate certificate, X509Chain chain,
            SslPolicyErrors errors)
            {
                return (true);
            };
        }

        // Update is called once per frame
        void Update()
        {

        }

        public void RegisterPressed()
        {
            LoginPanel.SetActive(false);
            RegistrationPanel.SetActive(true);
            ApplicationContext.ActivePanel = RegistrationPanel;
        }

        public void ResetPasswordPressed()
        {
            LoginPanel.SetActive(false);
            PasswordResetPanel.SetActive(true);
            ApplicationContext.ActivePanel = PasswordResetPanel;
        }

        public async void Login()
        {
            // Get the form data, build the request.
            LoginRequest req = new LoginRequest();
            req.username = LoginPanelUsername.text;
            req.password = LoginPanelPassword.text;

            UnityEngine.Debug.Log(JsonUtility.ToJson(req));
            try
            {
                // Request the login from the server
                LoginResponse response = await RequestLogin(req);
                // if successful, save the userID and authToken, move to next screen

                ApplicationContext.UserName = req.username;
                ApplicationContext.UserId = response.userId;
                ApplicationContext.AuthToken = response.authToken;
                LoginPanel.SetActive(false);
                HomePanel.SetActive(true);
                ApplicationContext.ActivePanel = HomePanel;
            }
            catch (Exception e)
            {
                // if not successful, indicate failure
                UnityEngine.Debug.Log("Exception in login: " + e.Message);
            }


        }


        private async Task<LoginResponse> RequestLogin(LoginRequest request)
        {

            string data = JsonUtility.ToJson(request);
            byte[] bytes = Encoding.UTF8.GetBytes(data);
            string loginEndpoint = String.Format("{0}/login", serverURL);
            UnityEngine.Debug.Log(loginEndpoint);

            HttpWebRequest serverRequest = (HttpWebRequest)WebRequest.Create(loginEndpoint);
            serverRequest.Method = "POST";
            serverRequest.ContentType = "application/json";
            serverRequest.ContentLength = bytes.Length;
            Stream dataStream = serverRequest.GetRequestStream();
            dataStream.Write(bytes, 0, bytes.Length);
            dataStream.Close();

            HttpWebResponse response = (HttpWebResponse)(await serverRequest.GetResponseAsync());
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string jsonResponse = reader.ReadToEnd();
            UnityEngine.Debug.Log(jsonResponse);
            LoginResponse info = JsonUtility.FromJson<LoginResponse>(jsonResponse);
            return info;


        }

        public async void Register()
        {
            // Get the form data, build the request.
            RegistrationRequest req = new RegistrationRequest();
            req.firstName = RegPanelFirstName.text;
            req.lastName = RegPanelLastName.text;
            req.email = RegPanelEmail.text;
            req.password = RegPanelPassword.text;

            int birthMonth = int.Parse(RegPanelBirthMonth.options[RegPanelBirthMonth.value].text);
            int birthYear = int.Parse(RegPanelBirthYear.options[RegPanelBirthYear.value].text);

            DateTime dt = new DateTime(birthYear, birthMonth, 1);
            DateTimeOffset dto = new DateTimeOffset(dt);
            req.birthDate = dto.ToUnixTimeMilliseconds();

            UnityEngine.Debug.Log(JsonUtility.ToJson(req));

            try
            {
                // Request the registration from the server
                RegistrationResponse response = await RequestRegistration(req);
                
                // Put up message box...

                LoginPanel.SetActive(true);
                RegistrationPanel.SetActive(false);
                ApplicationContext.ActivePanel = LoginPanel;
            }
            catch (Exception e)
            {
                // if not successful, indicate failure
                UnityEngine.Debug.Log("Exception in registration: " + e.Message);
            }

        }

        private async Task<RegistrationResponse> RequestRegistration(RegistrationRequest request)
        {
            string data = JsonUtility.ToJson(request);
            byte[] bytes = Encoding.UTF8.GetBytes(data);

            string loginEndpoint = String.Format("{0}/register", serverURL);
            UnityEngine.Debug.Log(loginEndpoint);

            HttpWebRequest serverRequest = (HttpWebRequest)WebRequest.Create(loginEndpoint);
            serverRequest.Method = "POST";
            serverRequest.ContentType = "application/json";
            serverRequest.ContentLength = bytes.Length;
            Stream dataStream = serverRequest.GetRequestStream();
            dataStream.Write(bytes, 0, bytes.Length);
            dataStream.Close();

            HttpWebResponse response = (HttpWebResponse)(await serverRequest.GetResponseAsync());
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string jsonResponse = reader.ReadToEnd();
            UnityEngine.Debug.Log(jsonResponse);
            RegistrationResponse info = JsonUtility.FromJson<RegistrationResponse>(jsonResponse);
            return info;


        }

        public async void Reset()
        {
            // Get the form data, build the request.
            ResetRequest req = new ResetRequest();
            req.email = ResetPanelEmail.text;

            UnityEngine.Debug.Log(JsonUtility.ToJson(req));

            try
            {
                // Request the reset from the server
                ResetResponse response = await RequestReset(req);

                // Put up message box...

                LoginPanel.SetActive(true);
                PasswordResetPanel.SetActive(false);
                ApplicationContext.ActivePanel = LoginPanel;
            }
            catch (Exception e)
            {
                // if not successful, indicate failure
                UnityEngine.Debug.Log("Exception in reset: " + e.Message);
            }
        }

        private async Task<ResetResponse> RequestReset(ResetRequest request)
        {
            string data = JsonUtility.ToJson(request);
            byte[] bytes = Encoding.UTF8.GetBytes(data);

            string resetEndpoint = String.Format("{0}/reset", serverURL);

            HttpWebRequest serverRequest = (HttpWebRequest)WebRequest.Create(resetEndpoint);
            serverRequest.Method = "POST";
            serverRequest.ContentType = "application/json";
            serverRequest.ContentLength = bytes.Length;
            Stream dataStream = serverRequest.GetRequestStream();
            dataStream.Write(bytes, 0, bytes.Length);
            dataStream.Close();

            HttpWebResponse response = (HttpWebResponse)(await serverRequest.GetResponseAsync());
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string jsonResponse = reader.ReadToEnd();
            UnityEngine.Debug.Log(jsonResponse);
            ResetResponse info = JsonUtility.FromJson<ResetResponse>(jsonResponse);
            return info;

        }
    }

}
