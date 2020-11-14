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
        public GameObject HomeScreen;

        private Color ColorLow;
        private Color ColorLowModerate;
        private Color ColorModerate;
        private Color ColorModerateHigh;
        private Color ColorHigh;

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

            ColorUtility.TryParseHtmlString(Constants.GRADIENT_1, out ColorLow);
            ColorUtility.TryParseHtmlString(Constants.GRADIENT_2, out ColorLowModerate);
            ColorUtility.TryParseHtmlString(Constants.GRADIENT_3, out ColorModerate);
            ColorUtility.TryParseHtmlString(Constants.GRADIENT_4, out ColorModerateHigh);
            ColorUtility.TryParseHtmlString(Constants.GRADIENT_5, out ColorHigh);

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
                if (null != response && null != response.headacheDays)
                {
                    int index = 1;
                    foreach (int day in response.headacheDays)
                    {
                        GameObject indicator = GetChildWithName(HomeScreen, "Day" + index);
                        Image spr = indicator.GetComponent<Image>();
                        switch (day)
                        {
                            case 0:
                                break;
                            case 1:
                                // Don't like the white here, trying the cream.
                                // spr.color = ColorLow;
                                spr.color = ColorLowModerate;
                                break;
                            case 3:
                                spr.color = ColorModerate;
                                break;
                            case 5:
                                spr.color = ColorHigh;
                                break;
                            default:
                                break;
                        }

                        index++;
                    }
                }
                
                
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

        GameObject GetChildWithName(GameObject obj, string name)
        {
            Transform trans = obj.transform;
            Transform childTrans = trans.Find(name);
            if (childTrans != null)
            {
                return childTrans.gameObject;
            }
            else
            {
                return null;
            }
        }
    }
}