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
using clinvest.migraine;
using clinvest.migraine.Model;
using System.Diagnostics;

namespace clinvest.migraine.Controller
{

    public class ConsentController : MonoBehaviour
    {
        private static string serverURL;
        public TMP_Dropdown ConsentDropdown;
        public Button SubmitButton;

        public GameObject ThanksPanel;

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
            
            GetConsent();
            SubmitButton.interactable = false;
        }

        public async void SubmitConsent()
        {
            // Get the form data, build the request.
            ConsentSaveRequest req = new ConsentSaveRequest();
            req.userId = ApplicationContext.UserId;

            if (ConsentDropdown.options[ConsentDropdown.value].text == "Yes")
            {
                req.consent = true;
            }
            else
            {
                req.consent = false;
            }

            UnityEngine.Debug.Log(JsonUtility.ToJson(req));
            try
            {
                // Send the save request to the server
                ConsentSaveResponse response = await RequestConsentSave(req);
                // if successful, set the save button inactive
                SubmitButton.interactable = false;
                ThanksPanel.SetActive(true);
            }
            catch (Exception e)
            {
                // if not successful, indicate failure
                UnityEngine.Debug.Log("Exception saving consent: " + e.Message);
            }


        }

        private async Task<ConsentSaveResponse> RequestConsentSave(ConsentSaveRequest request)
        {

            string data = JsonUtility.ToJson(request);
            byte[] bytes = Encoding.UTF8.GetBytes(data);
            string saveEndpoint = String.Format("{0}/user/consent/save?auth={1}", serverURL, ApplicationContext.AuthToken);
            UnityEngine.Debug.Log(saveEndpoint);

            HttpWebRequest serverRequest = (HttpWebRequest)WebRequest.Create(saveEndpoint);
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
            ConsentSaveResponse info = JsonUtility.FromJson<ConsentSaveResponse>(jsonResponse);
            return info;
        }

        public async void GetConsent()
        {
            try
            {
                // Send the request to the server
                ConsentResponse response = await RequestConsent();
                // if successful, set the fields
               

                    if (response.consent)
                    {
                        ConsentDropdown.value = ConsentDropdown.options.FindIndex(option => option.text == "Yes");
                    }
                    else
                    {
                    ConsentDropdown.value = ConsentDropdown.options.FindIndex(option => option.text == "No");
                    }
            }
            catch (Exception e)
            {
                // if not successful, indicate failure
                UnityEngine.Debug.Log("Exception requesting consent: " + e.Message);
            }
        }

        private async Task<ConsentResponse> RequestConsent()
        {
            string requestEndpoint = String.Format("{0}/user/consent/get?auth={1}", serverURL, ApplicationContext.AuthToken);
            UnityEngine.Debug.Log(requestEndpoint);

            ConsentRequest request = new ConsentRequest();
            request.userId = ApplicationContext.UserId;
            string data = JsonUtility.ToJson(request);
            byte[] bytes = Encoding.UTF8.GetBytes(data);

            HttpWebRequest serverRequest = (HttpWebRequest)WebRequest.Create(requestEndpoint);
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
            ConsentResponse info = JsonUtility.FromJson<ConsentResponse>(jsonResponse);
            return info;
        }

        // Update is called once per frame
        void Update()
        {

        }

        public void SubmitButtonActive()
        {
            SubmitButton.interactable = true;
        }
    }
}