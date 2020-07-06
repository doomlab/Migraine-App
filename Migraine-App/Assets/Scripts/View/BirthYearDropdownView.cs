using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

namespace clinvest.migraine.View
{

    public class BirthYearDropdownView : MonoBehaviour
    {
        private const int MAX_YEAR = 2005;
        private const int MIN_YEAR = 1910;

        public TMP_Dropdown BirthYearDropdown;

        // Start is called before the first frame update
        void Start()
        {
            List<string> opts = new List<string>();
            int year = MAX_YEAR;
            while (year > MIN_YEAR)
            {
                year = year - 1;
                opts.Add(year.ToString());
            }
            BirthYearDropdown.AddOptions(opts);
        }

        // Update is called once per frame
        void Update()
        {

        }
    }

}
