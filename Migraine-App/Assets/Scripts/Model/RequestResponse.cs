using System;

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
        public string username;
    }

    [Serializable]
    public class ResetResponse
    {
        public bool success;
        public bool message;
    }
}
