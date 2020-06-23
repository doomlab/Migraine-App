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

        //Button
        public button Next_1;
        public button Next_2;
        public button Next_3;
        public button Next_4;
        public button Next_5;
        public button Next_6;
        public button Next_7;
        public button Next_8;
        public button Next_9;
        public button Next_10;
        public button Submit_2;
        public button Submit_1;

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
        public void ErrorButtonPressed() => ErrorPanel.SetActive(false);

        public void Next_1Pressed()
        {
            if (Selection_1.value = 1)
            {

            }
            if (Selection_1.value = 0)
            {
                ErrorPanel.SetActive(true);
            }
            else
            {
                PanelOne.SetActive(false);
                PanelTwo.SetActive(true);
            }
        }
        public void Next_2Pressed()
        {
            if (Selection_2.value != 0)
            {
                PanelTwo.SetActive(false);
                PanelThree.SetActive(true);
            }
            else
            {
                ErrorPanel.SetActive(true);
            }
        }
        public void Next_3Pressed()
        {
            PanelThree.SetActive(false);
            PanelFour.SetActive(true);
        }
        public void Next_4Pressed()
        {
            if (Selection_3.value != 0)
            {
                PanelFour.SetActive(false);
                PanelFive.SetActive(true);
            }
            else
            {
                ErrorPanel.SetActive(true);
            }
        }
        public void Next_5Pressed()
        {
            if (Selection_4.value != 0)
            {
                PanelFive.SetActive(false);
                PanelSix.SetActive(true);
            }
            else
            {
                ErrorPanel.SetActive(true);
            }
        }
        public void Next_6Pressed()
        {
            if (Selection_5.value != 0)
            {
                PanelSix.SetActive(false);
                PanelSeven.SetActive(true);
            }
            else
            {
                ErrorPanel.SetActive(true);
            }
        }
        public void Next_7Pressed()
        {
            if (Selection_6.value != 0)
            {
                PanelSeven.SetActive(false);
                PanelEight.SetActive(true);
            }
            else
            {
                ErrorPanel.SetActive(true);
            }
        }
        public void Next_8Pressed()
        {
            if (Selection_7.value != 0)
            {
                PanelEight.SetActive(false);
                PanelNine.SetActive(true);
            }
            else
            {
                ErrorPanel.SetActive(true);
            }
        }
        public void Next_9Pressed()
        {
            if (Selection_8.value = 1)
            {
                PanelNine.SetActive(false);
                PanelTen.SetActive(true);
            }
            if (Selection_8.value = 2)
            {

            }
            else
            {
                ErrorPanel.SetActive(true);
            }
        }
        public void Next_10Pressed()
        {
            if (selection_10.value = 1)
            {
                PanelTen.SetActive(false);
                PanelNine.SetActive(true);
            }
            if (Selection_10.value = 2)
            {

            }
            else
            {
                ErrorPanel.SetActive(true);
            }
        }
    }
}
