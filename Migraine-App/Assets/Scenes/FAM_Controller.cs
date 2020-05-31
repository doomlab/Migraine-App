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
        public Button Next1;
        public Button Next2;
        public Button Next3;
        public Button Next4;
        public Button Submit;

        //Error 
        public GameObject ErrorPanel;
        public Button ErrorButton;

        // Panels
        public GameObject PageOnePanel;
        public GameObject PageTwoPanel;
        public GameObject PageThreePanel;
        public GameObject PageFourPanel;
        public GameObject PageFivePanel;

        // Start is called before the first frame update
        void Start()
        {

        }

        // Update is called once per frame
        void Update()
        {

        }

        public void ErrorButtonPressed()
        {
            ErrorPanel.SetActive(false);
        }

        public void Next1Pressed()
        {
            if ((Question1.value != 0) & (Question2.value != 0) & (Question3.value != 0) & (Question4.value != 0) & (Question5.value != 0) & (Question6.value != 0))
            {
                PageOnePanel.SetActive(false);
                PageTwoPanel.SetActive(true);
            }
            else
            {
                ErrorPanel.SetActive(true);
            }
        }
        public void Next2Pressed()
        {
            if ((Question7.value != 0) & (Question8.value != 0) & (Question9.value != 0) & (Question10.value != 0) & (Question11.value != 0) & (Question12.value != 0))
            {
                PageTwoPanel.SetActive(false);
                PageThreePanel.SetActive(true);
            }
            else
            {
                ErrorPanel.SetActive(true);
            }

        }
        public void Next3Pressed()
        {
            if ((Question13.value != 0) & (Question14.value != 0) & (Question15.value != 0) & (Question16.value != 0) & (Question17.value != 0))
            {
                PageThreePanel.SetActive(false);
                PageFourPanel.SetActive(true);
            }
            else
            {
                ErrorPanel.SetActive(true);
            }

        }
        public void Next4Pressed()
        {
            if ((Question18.value != 0) & (Question19.value != 0) & (Question20.value != 0) & (Question21.value != 0) & (Question22.value != 0))
            {
                PageFourPanel.SetActive(false);
                PageFivePanel.SetActive(true);
            }
            else
            {
                ErrorPanel.SetActive(true);
            }
            
        }
        public void SubmitPressed()
        {
            if ((Question23.value != 0) & (Question24.value != 0) & (Question25.value != 0) & (Question26.value != 0) & (Question27.value != 0))
            {
                SceneManager.LoadScene("homescreen");
            }
            else
            {
                ErrorPanel.SetActive(true);
            }
        }
    }
}
