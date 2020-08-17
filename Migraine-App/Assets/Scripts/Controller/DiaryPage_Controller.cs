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
        public Button Next_1;
        public Button Next_2;
        public Button Next_3;
        public Button Next_4;
        public Button Next_5;
        public Button Next_6;
        public Button Next_7;
        public Button ReturnButton;

        //Submit
        public GameObject SubmitPanel;
        public Button SubmitButton;

        //Error
        public GameObject ErrorPanel;
        public Button ErrorButton;
        public Button SkipButton;

        //Panels
        public GameObject PanelOne;
        public GameObject PanelTwo;
        public GameObject PanelThree;
        public GameObject PanelFour;
        public GameObject PanelFive;
        public GameObject PanelSix;
        public GameObject PanelSeven;

        public int currentpanel = 0;

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
            if (currentpanel == 1)
            {
                PanelOne.SetActive(true);
            }
            if (currentpanel == 2)
            {
                PanelTwo.SetActive(true);
            }
            if (currentpanel == 3)
            {
                PanelThree.SetActive(true);
            }
            if (currentpanel == 4)
            {
                PanelFour.SetActive(true);
            }
            if (currentpanel == 5)
            {
                PanelFive.SetActive(true);
            }
            if (currentpanel == 6)
            {
                PanelSix.SetActive(true);
            }
            if (currentpanel == 7)
            {
                PanelSeven.SetActive(true);
            }
        }

        public void SubmitButtonPressed() => SceneManager.LoadScene("homescreen");

        public void ReturnButtonPressed()
        {
            SubmitPanel.SetActive(false);
            if (currentpanel == 1)
            {
                PanelOne.SetActive(true);
            }
            if (currentpanel == 2)
            {
                PanelTwo.SetActive(true);
            }
            if (currentpanel == 3)
            {
                PanelThree.SetActive(true);
            }
            if (currentpanel == 4)
            {
                PanelFour.SetActive(true);
            }
            if (currentpanel == 5)
            {
                PanelFive.SetActive(true);
            }
            if (currentpanel == 6)
            {
                PanelSix.SetActive(true);
            }
            if (currentpanel == 7)
            {
                PanelSeven.SetActive(true);
            }
        }
            
        public void SkipButtonPressed()
        {
            ErrorPanel.SetActive(false);
            if (currentpanel == 7)
            {
                SceneManager.LoadScene("homescreen");
            }
            else if (currentpanel == 1)
            {
                PanelOne.SetActive(false);
                PanelTwo.SetActive(true);
            }
            else if (currentpanel == 2)
            {
                PanelTwo.SetActive(false);
                PanelThree.SetActive(true);
            }
            else if (currentpanel == 3)
            {
                PanelThree.SetActive(false);
                PanelFour.SetActive(true);
            }
            else if (currentpanel == 4)
            {
                PanelFour.SetActive(false);
                PanelFive.SetActive(true);
            }
            else if (currentpanel == 5)
            {
                PanelFive.SetActive(false);
                PanelSix.SetActive(true);
            }
            else if (currentpanel == 6)
            {
                PanelSix.SetActive(false);
                PanelSeven.SetActive(true);
            }
            currentpanel += 1;
        }

        public void Next_1Pressed()
        {
            if (Selection_1.value != 0)
            {
                if (Selection_1.value == 1)
                {
                    PanelOne.SetActive(false);
                    SubmitPanel.SetActive(true);
                }
                else
                {
                    PanelOne.SetActive(false);
                    PanelTwo.SetActive(true);
                    currentpanel += 1;
                }
            }
            else
            {
                PanelOne.SetActive(false);
                ErrorPanel.SetActive(true);
            }
        }
        public void Next_2Pressed()
        {
            if (Selection_2.value != 0)
            {
                PanelTwo.SetActive(false);
                PanelThree.SetActive(true);
                currentpanel += 1;
            }
            else
            {
                PanelTwo.SetActive(false);
                ErrorPanel.SetActive(true);
            }
        }
        public void Next_3Pressed()
        {
            PanelThree.SetActive(false);
            PanelFour.SetActive(true);
            currentpanel += 1;
        }
        public void Next_4Pressed()
        {
            if ((Selection_3.value != 0) & (Selection_4.value != 0) & (Selection_5.value != 0))
            {
                PanelFour.SetActive(false);
                PanelFive.SetActive(true);
                currentpanel += 1;
            }
            else
            {
                PanelFour.SetActive(false);
                ErrorPanel.SetActive(true);
            }
        } 
        public void Next_5Pressed()
        {
            if ((Selection_6.value != 0) & (Selection_7.value != 0))
            {
                PanelFive.SetActive(false);
                PanelSix.SetActive(true);
                currentpanel += 1;
            }
            else
            {
                PanelFive.SetActive(false);
                ErrorPanel.SetActive(true);
            }
        }
        public void Next_6Pressed()
        {
            if (Selection_8.value == 1)
            {
                PanelSix.SetActive(false);
                PanelSeven.SetActive(true);
                currentpanel += 1;
            }
            else if (Selection_8.value == 2)
            {
                PanelSix.SetActive(false); 
                SubmitPanel.SetActive(true);
            }
            else
            {
                PanelSix.SetActive(false);
                ErrorPanel.SetActive(true);
            }
        }
        public void Next_7Pressed()
        {
            if ((Selection_10.value == 0) || (Selection_9.value == 0))
            {
                PanelSeven.SetActive(false);
                ErrorPanel.SetActive(true);
            }
            else if (Selection_9.value == 2)
            {
                PanelSeven.SetActive(false);
                SubmitPanel.SetActive(true);
            }
            else if (Selection_9.value == 1)
            {
                PanelSeven.SetActive(false);
                PanelSeven.SetActive(true);
            }
        }
    }
}