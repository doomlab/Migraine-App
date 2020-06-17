using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using TMPro;
using UnityEngine;
using UnityEngine.IU;
using clinvest.migraine.Model;
using UnityEngine.ScreenManagement;

namespace clinvest.migraine.Controller
    public class DiaryPage_Controller : MonoBehaviour
    {
        //Input Boxes
        public TMP_Dropdown Selection_1;
        public TMP_Dropdown Selection_2;
        public TMP_Dropdown Selection_3;
        public TMP_Dropdown Selection_4;
        public TMP_Dropdown Selection_5;
        public TMP_Dropdown Selection_6;
        public TMP_Dropdown Selection_7;
        public TMP_Dropdown Selection_8;
        public TMP_Dropdown Selection_9;
        public TMP_Dropdown Selection_10;
        public TMP_Dropdown Selection_11;
        public TMP_Dropdown Selection_12;
        public TMP_Dropdown Selection_13;

        //Button
        public Button Next_1;
        public Button Next_2;
        public Button Next_3;
        public Button Next_4;
        public Button Next_5;
        public Button Next_6;
        public Button Next_7;
        public Button Next_8;
        public Button Next_9;
        public Button Submit;

        //Error
        public GameObject ErrorPanel;
        public Button ErrorButton;

        //Panels
        public GameObject PanelOne;
        public GameObject PanelTwo;
        public GameObject PanelThree;
        public GameObject PanelFour;
        public GameObject PanelFive;
        public GameObject PanelSix;
        public GameObject PanelSeven;
        public GameObject PanelEight;
        public GameObject PanelNine;
        public GameObject PanelTen;

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
        public void Next_1Pressed()
        {
            if (Selection_1.value = 1)
            {
                
            }
            if (Selection_1.value = 0)
            {

            }
            else
            {
                PanelOne.SetActive(false)
                PanelTwo.SetActive(true)
            }
        }
    }
