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
    public class NavScreen_Controller : MonoBehaviour
    {
        //Buttons
        public Button Sandwich;
        public Button Diary;
        public Button FAMS;
        public Button Log;
        public Button Settings;

        //Panels
        public GameObject NavPanel;

        public void SandwichButtonPressed()
        {
            if (NavPanel.active == false)
            {
                NavPanel.SetActive(true);
            }
            else
            {
                NavPanel.SetActive(false);
            }

        }
        public void DiaryButtonPressed()
        {
            SceneManager.LoadScene("Diary_One_Scene");
        }
        public void FAMSButtonPressed()
        {
            SceneManager.LoadScene("FAMS_One_Scene");
        }
        public void LogButtonPressed()
        {
            SceneManager.LoadScene("Log_One_Scene");
        }
        public void SettingsButtonPressed()
        {
            SceneManager.LoadScene("Settings_One_Scene");
        }
    }
    
}
