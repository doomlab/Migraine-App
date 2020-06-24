public static class ApplicationContext
{
    private static string userName;
    private static string userId;
    private static string authToken;

    public static string UserName 
    {
        get 
        {
            return userName;
        }
        set 
        {
            userName = value;
        }
    }

    public static string UserId 
    {
        get 
        {
            return userId;
        }
        set 
        {
            userId = value;
        }
    }

    public static string AuthToken 
    {
        get 
        {
            return authToken;
        }
        set 
        {
            authToken = value;
        }
    }
}
