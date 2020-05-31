using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using TMPro;
using UnityEngine;
using UnityEngine.UI;
using clinvest.migraine.Model;

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

        // Panels
        public GameObject LoginPanel;
        public GameObject RegistrationPanel;
        public GameObject PasswordResetPanel;


        // Start is called before the first frame update
        void Start()
        {
            if (PlayerPrefs.HasKey("server_url"))
            {
                serverURL = PlayerPrefs.GetString("server_url");
            }
            else
            {
                serverURL = "http://localhost:5309";
            }
            LoginPanel.SetActive(true);
            PasswordResetPanel.SetActive(false);
            RegistrationPanel.SetActive(false);
        }

        // Update is called once per frame
        void Update()
        {

        }

        public void RegisterPressed()
        {
            LoginPanel.SetActive(false);
            RegistrationPanel.SetActive(true);
        }

        public void ResetPasswordPressed()
        {
            LoginPanel.SetActive(false);
            PasswordResetPanel.SetActive(true);
        }

        public async void Login()
        {
            // Get the form data, build the request.
            LoginRequest req = new LoginRequest();
            req.username = LoginPanelUsername.text;
            req.password = LoginPanelPassword.text;

            Debug.Log(req.username);
            Debug.Log(req.password);
            Debug.Log(JsonUtility.ToJson(req));

            // Request the login from the server
            // LoginResponse response = await RequestLogin(req);
            // if successful, save the userID and authToken, move to next screen

            // if not successful, indicate failure
        }


        private async Task<LoginResponse> RequestLogin(LoginRequest request)
        {

            string data = String.Format("data='{0}'", JsonUtility.ToJson(request));
            byte[] bytes = Encoding.UTF8.GetBytes(data);

            string loginEndpoint = String.Format("{0}/login", serverURL);

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

            Debug.Log(JsonUtility.ToJson(req));

            // Request the registration from the server

            // if not successful, indicate failure
        }

        private async Task<RegistrationResponse> RequestRegistration(RegistrationRequest request)
        {

            string data = String.Format("data='{0}'", JsonUtility.ToJson(request));
            byte[] bytes = Encoding.UTF8.GetBytes(data);

            string loginEndpoint = String.Format("{0}/register", serverURL);

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
            RegistrationResponse info = JsonUtility.FromJson<RegistrationResponse>(jsonResponse);
            return info;


        }

        public async void Reset()
        {
            // Get the form data, build the request.


            // Request the login from the server


            // if not successful, indicate failure
        }

        private async Task<ResetResponse> RequestReset(ResetRequest request)
        {
            ResetResponse info = null;
            return info;

        }
    }

}
