using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace clinvest.migraine.Controller
{
    public class ThanksPanelController : MonoBehaviour
    {
        public GameObject ThanksPanel;

        // Start is called before the first frame update
        void Start()
        {

        }

        // Update is called once per frame
        void Update()
        {

        }

        public void Dismiss()
        {
            ThanksPanel.SetActive(false);
        }
    }
}