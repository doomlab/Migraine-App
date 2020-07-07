using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
namespace clinvest.migraine.View
{

    public class DiagnosisYearDropdownView : MonoBehaviour
    {
        private int MAX_YEAR = System.DateTime.Now.Year;
        private int MIN_YEAR = System.DateTime.Now.Year - 100;

        public TMP_Dropdown DiagnosisYearDropdown;

        // Start is called before the first frame update
        void Start()
        {
            List<string> opts = new List<string>();
            int year = MAX_YEAR;
            while (year > MIN_YEAR)
            {      
                opts.Add(year.ToString());
                year = year - 1;
            }
            DiagnosisYearDropdown.AddOptions(opts);
        }

        // Update is called once per frame
        void Update()
        {

        }
    }
}
