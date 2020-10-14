using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace clinvest.migraine.Controller
{

    public class StartupController : MonoBehaviour
    {
        public GameObject LoginPanel;
        public GameObject RegistrationPanel;
        public GameObject PasswordResetPanel;
        public GameObject HomePanel;

        // Start is called before the first frame update
        void Start()
        {
            PasswordResetPanel.SetActive(false);
            RegistrationPanel.SetActive(false);
            if (null != ApplicationContext.AuthToken)
            {
                HomePanel.SetActive(true);
                ApplicationContext.ActivePanel = HomePanel;
                LoginPanel.SetActive(false);
            }
            else
            {
                HomePanel.SetActive(false);
                LoginPanel.SetActive(true);
                ApplicationContext.ActivePanel = LoginPanel;
            }
        }

        // Update is called once per frame
        void Update()
        {

        }
    }
}