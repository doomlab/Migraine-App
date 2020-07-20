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
using UnityEngine.SceneManagement;


namespace clinvest.migraine.Controller
{
    public class FAM_Controller : MonoBehaviour
    {

        private static string serverURL;

        // Input Boxes 
        public TMP_Dropdown Question1;
        public TMP_Dropdown Question2;
        public TMP_Dropdown Question3;
        public TMP_Dropdown Question4;
        public TMP_Dropdown Question5;
        public TMP_Dropdown Question6;
        public TMP_Dropdown Question7;
        public TMP_Dropdown Question8;
        public TMP_Dropdown Question9;
        public TMP_Dropdown Question10;
        public TMP_Dropdown Question11;
        public TMP_Dropdown Question12;
        public TMP_Dropdown Question13;
        public TMP_Dropdown Question14;
        public TMP_Dropdown Question15;
        public TMP_Dropdown Question16;
        public TMP_Dropdown Question17;
        public TMP_Dropdown Question18;
        public TMP_Dropdown Question19;
        public TMP_Dropdown Question20;
        public TMP_Dropdown Question21;
        public TMP_Dropdown Question22;
        public TMP_Dropdown Question23;
        public TMP_Dropdown Question24;
        public TMP_Dropdown Question25;
        public TMP_Dropdown Question26;
        public TMP_Dropdown Question27;

        // Button
        public Button Begin;
        public Button Next1;
        public Button Next2;
        public Button Next3;
        public Button Next4;
        public Button Next5;
        public Button Submit;
        public Button Skip;

        //Error 
        public GameObject ErrorPanel;
        public Button ErrorButton;

        // Panels
        public GameObject InstructionPanel;
        public GameObject PageOnePanel;
        public GameObject PageTwoPanel;
        public GameObject PageThreePanel;
        public GameObject PageFourPanel;
        public GameObject PageFivePanel;
        public GameObject PageSixPanel;

        public int currentpanel = 0;

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
        }

        // Update is called once per frame
        void Update()
        {

        }
        public void SkipButtonPressed()
        {
            ErrorPanel.SetActive(false);
            if (currentpanel == 6)
            {
                SceneManager.LoadScene("homescreen");
            }
            else if (currentpanel == 1)
            {
                PageOnePanel.SetActive(false);
                PageTwoPanel.SetActive(true);
            }
            else if (currentpanel == 2)
            {
                PageTwoPanel.SetActive(false);
                PageThreePanel.SetActive(true);
            }
            else if (currentpanel == 3)
            {
                PageThreePanel.SetActive(false);
                PageFourPanel.SetActive(true);
            }
            else if (currentpanel == 4)
            {
                PageFourPanel.SetActive(false);
                PageFivePanel.SetActive(true);
            }
            else if (currentpanel == 5)
            {
                PageFivePanel.SetActive(false);
                PageSixPanel.SetActive(true);
            }
            currentpanel += 1;
        }
        public void ErrorButtonPressed()
        {
            ErrorPanel.SetActive(false);
        }
        public void BeginPressed()
        {
            InstructionPanel.SetActive(false);
            PageOnePanel.SetActive(true);
            currentpanel += 1;
        }
        public void Next1Pressed()
        {
            if ((Question1.value != 0) & (Question2.value != 0) & (Question3.value != 0) & (Question4.value != 0) & (Question5.value != 0))
            {
                PageOnePanel.SetActive(false);
                PageTwoPanel.SetActive(true);
                currentpanel += 1;
            }
            else
            {
                ErrorPanel.SetActive(true);
            }
        }

        public void Next2Pressed()
        {
            if ((Question6.value != 0) & (Question7.value != 0) & (Question8.value != 0) & (Question9.value != 0) & (Question10.value != 0))
            {
                PageTwoPanel.SetActive(false);
                PageThreePanel.SetActive(true);
                currentpanel += 1;
            }
            else
            {
                ErrorPanel.SetActive(true);
            }

        }
        public void Next3Pressed()
        {
            if ((Question11.value != 0) & (Question12.value != 0) & (Question13.value != 0) & (Question14.value != 0))
            {
                PageThreePanel.SetActive(false);
                PageFourPanel.SetActive(true);
                currentpanel += 1;
            }
            else
            {
                ErrorPanel.SetActive(true);
            }

        }
        public void Next4Pressed()
        {
            if ((Question15.value != 0) & (Question16.value != 0) & (Question17.value != 0) & (Question18.value != 0))
            {
                PageFourPanel.SetActive(false);
                PageFivePanel.SetActive(true);
                currentpanel += 1;
            }
            else
            {
                ErrorPanel.SetActive(true);
            }

        }
        public void Next5Pressed()
        {
            if ((Question19.value != 0) & (Question20.value != 0) & (Question21.value != 0) & (Question22.value != 0) & (Question23.value != 0))
            {
                PageFivePanel.SetActive(false);
                PageSixPanel.SetActive(true);
                currentpanel +=1;
            }
            else
            {
                ErrorPanel.SetActive(true);
            }

        }
        public void SubmitPressed()
        {
            if ((Question24.value != 0) & (Question25.value != 0) & (Question26.value != 0) & (Question27.value != 0))
            {
                SceneManager.LoadScene("homescreen");
            }
            else
            {
                ErrorPanel.SetActive(true);
            }
        }
        public async void FAMSRequest()
        {
            //Request
            FAMSRequest req = new FAMSRequest();
            req.UserID = "1";
            req.Q1 = Question1.value;
            req.Q2 = Question2.value;
            req.Q3 = Question3.value;
            req.Q4 = Question4.value;
            req.Q5 = Question5.value;
            req.Q6 = Question6.value;
            req.Q7 = Question7.value;
            req.Q8 = Question8.value;
            req.Q9 = Question9.value;
            req.Q10 = Question10.value;
            req.Q11 = Question11.value;
            req.Q12 = Question12.value;
            req.Q13 = Question13.value;
            req.Q14 = Question14.value;
            req.Q15 = Question15.value;
            req.Q16 = Question16.value;
            req.Q17 = Question17.value;
            req.Q18 = Question18.value;
            req.Q19 = Question19.value;
            req.Q20 = Question20.value;
            req.Q21 = Question21.value;
            req.Q22 = Question22.value;
            req.Q23 = Question23.value;
            req.Q24 = Question24.value;
            req.Q25 = Question25.value;
            req.Q26 = Question26.value;
            req.Q27 = Question27.value;

        }

        /**private async Task<FAMSResponse> RequestFAMS(FAMSRequest request)
        {

            string data = String.Format("data='{0}'", JsonUtility.ToJson(request));
            byte[] bytes = Encoding.UTF8.GetBytes(data);

            string FAMSpoint = String.Format("{0}/login", serverURL);

            HttpWebRequest serverRequest = (HttpWebRequest)WebRequest.Create(FAMSEndpoint);
            serverRequest.Method = "POST";
            serverRequest.ContentType = "application/json";
            serverRequest.ContentLength = bytes.Length;
            Stream dataStream = serverRequest.GetRequestStream();
            dataStream.Write(bytes, 0, bytes.Length);
            dataStream.Close();

            HttpWebResponse response = (HttpWebResponse)(await serverRequest.GetResponseAsync());
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string jsonResponse = reader.ReadToEnd();
            FAMSResponse info = JsonUtility.FromJson<FAMSResponse>(jsonResponse);
            return info;


        }**/
    }
}
