using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

namespace clinvest.migraine.View {

public class YearDropdownView : MonoBehaviour
{
    public TMP_Dropdown YearDropdown;

    // Start is called before the first frame update
    void Start()
    {
       List<string> opts = new List<string>();
       int year = 2020;
       while (year > 1910)
       {
           year = year - 1;
           opts.Add(year.ToString());    
       }
       YearDropdown.AddOptions(opts);
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}

}
