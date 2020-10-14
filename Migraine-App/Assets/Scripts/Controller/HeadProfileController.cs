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

    public class HeadProfileController : MonoBehaviour
    {
        private static string serverURL;

        public TMP_Dropdown Diagnosed;
        public TMP_Dropdown StartYear;
        public TMP_Dropdown DiagnosedYear;
        public TMP_InputField Diagnosis;

        public Button SubmitButton;

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
            GetHeadProfile();
        }

        // Update is called once per frame
        void Update()
        {

        }

        public void SubmitButtonActive()
        {
            SubmitButton.interactable = true;
        }

        public async void SubmitHeadProfile()
        {
            // Get the form data, build the request.
            HeadProfileSaveRequest req = new HeadProfileSaveRequest();
            req.userId = ApplicationContext.UserId;
            
            if (Diagnosed.options[Diagnosed.value].text == "Yes")
            {
                req.diagnosed = true;
                req.diagnosis = Diagnosis.text;
                req.diagnosisYear = DiagnosedYear.options[DiagnosedYear.value].text;
            }
            else
            {
                req.diagnosed = false;
            }
            req.startYear = StartYear.options[StartYear.value].text;

            UnityEngine.Debug.Log(JsonUtility.ToJson(req));
            try
            {
                // Send the save request to the server
                HeadProfileSaveResponse response = await RequestHeadProfileSave(req);
                // if successful, set the save button inactive
                SubmitButton.interactable = false;
            }
            catch (Exception e)
            {
                // if not successful, indicate failure
                UnityEngine.Debug.Log("Exception saving head profile: " + e.Message);
            }


        }

        private async Task<HeadProfileSaveResponse> RequestHeadProfileSave(HeadProfileSaveRequest request)
        {

            string data = JsonUtility.ToJson(request);
            byte[] bytes = Encoding.UTF8.GetBytes(data);
            string saveEndpoint = String.Format("{0}/user/profile/save?auth={1}", serverURL, ApplicationContext.AuthToken);
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
            HeadProfileSaveResponse info = JsonUtility.FromJson<HeadProfileSaveResponse>(jsonResponse);
            return info;
        }

        public async void GetHeadProfile()
        {
            try
            {
                // Send the request to the server
                HeadProfileResponse response = await RequestHeadProfile();
                // if successful, set the fields
                if (null != response.startYear)
                {
                    // For now, assume if there's a start year, then other stuff is valid.
                    StartYear.value = StartYear.options.FindIndex(option => option.text == response.startYear);
                    
                    if (response.diagnosed)
                    {
                        Diagnosed.value = Diagnosed.options.FindIndex(option => option.text == "Yes");
                        DiagnosedYear.value = DiagnosedYear.options.FindIndex(option => option.text == response.diagnosisYear);
                        Diagnosis.text = response.diagnosis;
                    }
                    else
                    {
                        Diagnosed.value = Diagnosed.options.FindIndex(option => option.text == "No");
                    }
                }
            }
            catch (Exception e)
            {
                // if not successful, indicate failure
                UnityEngine.Debug.Log("Exception requesting head profile: " + e.Message);
            }
        }

        private async Task<HeadProfileResponse> RequestHeadProfile()
        {
            string requestEndpoint = String.Format("{0}/user/profile/get?auth={1}", serverURL, ApplicationContext.AuthToken);
            UnityEngine.Debug.Log(requestEndpoint);

            HeadProfileRequest request = new HeadProfileRequest();
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
            HeadProfileResponse info = JsonUtility.FromJson<HeadProfileResponse>(jsonResponse);
            return info;
        }
    }
}
