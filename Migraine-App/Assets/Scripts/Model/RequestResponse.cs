using System;
using System.Collections.Generic;

namespace clinvest.migraine.Model
{
    [Serializable]
    public class LoginRequest
    {
        public string username;
        public string password;
    }

    [Serializable]
    public class LoginResponse
    {
        public string userId;
        public string authToken;
    }

    [Serializable]
    public class RegistrationRequest
    {
        public string firstName;
        public string lastName;
        public long birthDate;
        public string sex;
        public string email;
        public string password;
    }

    [Serializable]
    public class RegistrationResponse
    {
        public bool success;
        public bool message;
    }

    [Serializable]
    public class ResetRequest
    {
        public string email;
    }

    [Serializable]
    public class ResetResponse
    {
        public bool success;
        public bool message;
    }

    [Serializable]
    public class FAMSRequest
    {
        public string userId;
        public long studyId;
        public int Q1;
        public int Q2;
        public int Q3;
        public int Q4;
        public int Q5;
        public int Q6;
        public int Q7;
        public int Q8;
        public int Q9;
        public int Q10;
        public int Q11;
        public int Q12;
        public int Q13;
        public int Q14;
        public int Q15;
        public int Q16;
        public int Q17;
        public int Q18;
        public int Q19;
        public int Q20;
        public int Q21;
        public int Q22;
        public int Q23;
        public int Q24;
        public int Q25;
        public int Q26;
        public int Q27;
    }
    [Serializable]
    public class FAMSResponse
    {
        public bool success;
        public bool message;
    }

    [Serializable]
    public class DiaryMedication
    {
        long medicationId;
        String howOften;
        bool painDecrease;
    }

    [Serializable]
    public class DiarySaveRequest
    {
        public string severity;
        public bool newHeadache;
        public double hours;
        public bool painDirectional;
        public bool painThrobbing;
        public bool painWorse;
        public bool nausea;
        public bool lightSensitive;
        public bool soundSensitive;
        public string worstSymptom;
        public bool tookMedication;
        public List<DiaryMedication> medications;
    }
    [Serializable]
    public class DiarySaveResponse
    {
        public bool success;
        public bool message;
    }

    [Serializable]
    public class Medication
    {
        public long id;
        public string category;
        public string name;
        public string formulary;
        public string created;
        public string last_modified;
    }
    [Serializable]
    public class MedicationListResponse
    {
        public List<Medication> meds;
    }
    [Serializable]
    public class HeadProfileSaveRequest
    {
        public string userId;
        public bool diagnosed;
        public string diagnosisYear;
        public string diagnosis;
        public string startYear;
        public List<long> medications;
    }
    [Serializable]
    public class HeadProfileSaveResponse
    {
        public bool success;
        public bool message;
    }
    [Serializable]
    public class HeadProfileRequest
    {
        public string userId;
    }

    [Serializable]
    public class HeadProfileResponse
    {
        public string userId;
        public bool diagnosed;
        public string diagnosisYear;
        public string diagnosis;
        public string startYear;
        public List<long> medications;
    }


    [Serializable]
    public class ConsentRequest
    {
        public string userId;
    }

    [Serializable]
    public class ConsentResponse
    {
        public string userId;
        public bool consent;
    }

    [Serializable]
    public class ConsentSaveRequest
    {
        public string userId;
        public bool consent;
    }

    [Serializable]
    public class ConsentSaveResponse
    {
        public bool success;
        public bool message;
    }


}