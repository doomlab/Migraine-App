using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Threading.Tasks;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

using clinvest.migraine.Model;

namespace clinvest.migraine.View
{
    public class AcuteMedicationListView : MonoBehaviour
    {
        public TMP_Dropdown AcuteMedicationDropdown;
        private static string serverURL;
        public List<Medication> medications;

        // Start is called before the first frame update
        void Start()
        {
            if (PlayerPrefs.HasKey("server_url"))
            {
                serverURL = PlayerPrefs.GetString("server_url");
            }
            else
            {
                serverURL = "https://app.clinvest.com/migraine-server";
            }

            ServicePointManager.ServerCertificateValidationCallback = delegate (
            System.Object obj, X509Certificate certificate, X509Chain chain,
            SslPolicyErrors errors)
            {
                return (true);
            };
            LoadAcuteMedications();
        }

        public async void LoadAcuteMedications()
        {
            UnityEngine.Debug.Log("Requesting acute medication list.");
            try
            {
                // Request the list from the server
                MedicationListResponse response = await RequestAcuteMedications();
                UnityEngine.Debug.Log("Received acute medication list");
                medications = response.meds;
                PopulateList();
            }
            catch (Exception e)
            {
                // if not successful, indicate failure
                UnityEngine.Debug.Log("Exception requesting acute medication list: " + e.Message);
            }
        }

        private async Task<MedicationListResponse> RequestAcuteMedications()
        {
            string listEndpoint = String.Format("{0}/list/acute", serverURL);
            UnityEngine.Debug.Log(listEndpoint);

            HttpWebRequest serverRequest = (HttpWebRequest)WebRequest.Create(listEndpoint);
            serverRequest.Method = "GET";

            HttpWebResponse response = (HttpWebResponse)(await serverRequest.GetResponseAsync());
            StreamReader reader = new StreamReader(response.GetResponseStream());
            string jsonResponse = reader.ReadToEnd();
            UnityEngine.Debug.Log(jsonResponse);
            MedicationListResponse info = JsonUtility.FromJson<MedicationListResponse>(jsonResponse);
            return info;
        }

        private void PopulateList()
        {
            List<TMP_Dropdown.OptionData> options = new List<TMP_Dropdown.OptionData>();
            foreach (Medication m in medications)
            {
                string med = "";
                if (m.name != null && m.formulary != null)
                {
                    med = String.Format("{0} ({1})", m.name, m.formulary);
                }
                else if (m.name == null && m.formulary != null)
                {
                    med = String.Format("{0}", m.formulary);
                }
                else if (m.name != null && m.formulary == null)
                {
                    med = String.Format("{0}", m.name, m.id);
                }
                TMP_Dropdown.OptionData option = new TMP_Dropdown.OptionData(med);
                options.Add(option);
            }
            AcuteMedicationDropdown.AddOptions(options);
            AcuteMedicationDropdown.RefreshShownValue();
        }

        // Update is called once per frame
        void Update()
        {

        }
    }

}
