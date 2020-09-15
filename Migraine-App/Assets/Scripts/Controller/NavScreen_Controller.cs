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
        public Button SandwichButton;

        //Panels
        public GameObject NavPanel;
        public GameObject SetPanel;
        public GameObject Education;
        public GameObject Records;
        public GameObject Home_Screen;
        public GameObject FAMS;
        public GameObject Diary;
        public GameObject Head_Profile;
        public GameObject User_Setting;
        public GameObject Privacy_Policy;
        public GameObject Consent;
        public GameObject Register;
        public GameObject ForgotPassword;
        public GameObject Login;
        public GameObject TOS;
        public GameObject CurrentPanel;
        public GameObject Menstral;
        public GameObject Migrane;
        public GameObject Pomdrone;
        public GameObject Acute;

        void Start()
        {
            CurrentPanel = Home_Screen;
        }
        public void SandwichButtonPressed()
        {
            if (NavPanel.active == false)
            {
                NavPanel.SetActive(true);
                SandwichButton.gameObject.SetActive(false);
            }
            else
            {
                NavPanel.SetActive(false);
                SandwichButton.gameObject.SetActive(true);
                CurrentPanel.SetActive(true);
            }
        }
        public void DiaryButtonPressed()
        {
            NavPanel.SetActive(false);
            SandwichButton.gameObject.SetActive(true);
            if (CurrentPanel != Diary)
            {
                    Diary.SetActive(true);
                    CurrentPanel.SetActive(false);
                    CurrentPanel = Diary;
            }
        }
        public void FAMSButtonPressed()
        {
            NavPanel.SetActive(false);
            SandwichButton.gameObject.SetActive(true);
            if (CurrentPanel != FAMS)
            {
                FAMS.SetActive(true);
                CurrentPanel.SetActive(false);
                CurrentPanel = FAMS;
            }
        }
        public void LogoutButtonPressed()
        {
            NavPanel.SetActive(false);
            SandwichButton.gameObject.SetActive(true);
            if (CurrentPanel != Login)
            {
                Login.SetActive(true);
                CurrentPanel.SetActive(false);
                CurrentPanel = Login;
            }
        }
        public void SettingsButtonPressed()
        {
            NavPanel.SetActive(false);
            //SandwichButton.gameObject.SetActive(true);
            if (CurrentPanel != SetPanel)
            {
                SetPanel.SetActive(true);
                CurrentPanel.SetActive(false);
            }
        }
        public void RecordsButtonPressed()
        {
            NavPanel.SetActive(false);
            SandwichButton.gameObject.SetActive(true);
            if (CurrentPanel != Records)
            {
                Records.SetActive(true);
                CurrentPanel.SetActive(false);
                CurrentPanel = Records;
            }
        }
        public void UserSettinsButtonPressed()
        {
            NavPanel.SetActive(false);
            SandwichButton.gameObject.SetActive(true);
            if (CurrentPanel != Records)
            {
                User_Setting.SetActive(true);
                CurrentPanel.SetActive(false);
                CurrentPanel = User_Setting;
            }
        }
        public void EduButtonPressed()
        {
            NavPanel.SetActive(false);
            //SandwichButton.gameObject.SetActive(true);
            if (CurrentPanel != Education)
            {
                Education.SetActive(true);
                CurrentPanel.SetActive(false);
                //CurrentPanel = Education;
            }
        }
        public void Edu_BackButtonPressed()
        {
            NavPanel.SetActive(true);
        }
        public void Edu_MenstralPressed()
        {
            Menstral.SetActive(true);
        }
        public void Edu_Menstral_BackPressed()
        {
            Menstral.SetActive(false);
        }
        public void Edu_MigranePressed()
        {
            Migrane.SetActive(true);
        }
        public void Edu_Migrane_BackPressed()
        {
            Migrane.SetActive(false);
        }
        public void Edu_PomdronePressed()
        {
            Pomdrone.SetActive(true);
        }
        public void Edu_Pomdrone_BackPressed()
        {
            Pomdrone.SetActive(false);
        }
        public void Edu_AcutePressed()
        {
            Acute.SetActive(true);
        }
        public void Edu_Acute_BackPressed()
        {
            Acute.SetActive(false);
        }
        public void Settings_BackPressed()
        {
            NavPanel.SetActive(true);
            SetPanel.SetActive(false);
        }
        public void Settings_Sub_BackPressed()
        {
            CurrentPanel.SetActive(false);
            SetPanel.SetActive(true);
        }

    }
    
}
