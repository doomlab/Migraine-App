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
    }
    
}
