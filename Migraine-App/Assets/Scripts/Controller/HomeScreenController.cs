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

    public class HomeScreenController : MonoBehaviour
    {
        private static string serverURL;

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

            GetHeadacheDays();
        }

        // Update is called once per frame
        void Update()
        {

        }



        public async void GetHeadacheDays()
        {
            try
            {
                // Send the request to the server
                HeadacheDaysResponse response = await RequestHeadacheDays();
                // if successful, set the fields
                
                
            }
            catch (Exception e)
            {
                // if not successful, indicate failure
                UnityEngine.Debug.Log("Exception requesting headache days: " + e.Message);
            }
        }

        private async Task<HeadacheDaysResponse> RequestHeadacheDays()
        {
            string requestEndpoint = String.Format("{0}/headache/get_days?auth={1}", serverURL, ApplicationContext.AuthToken);
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
            HeadacheDaysResponse info = JsonUtility.FromJson<HeadacheDaysResponse>(jsonResponse);
            return info;
        }
    }
}