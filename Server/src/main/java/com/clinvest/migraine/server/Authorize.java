package com.clinvest.migraine.server;

import java.util.UUID;

import com.clinvest.migraine.server.data.UserSession;

public class Authorize
{
   public static boolean checkAuthToken(UUID authToken)
   {
     boolean retval = false;
     
     UserSession session = UserSession.getById(authToken);
     if (null != session) 
     {
       /*
       // Check to make sure the session hasn't expired
       // (Uncomment once first demo has passed)
       long timePassed = System.currentTimeMillis() - session.getActive().getTime();
       if (timePassed > UserSession.DEFAULT_SESSION_LENGTH)
       {
         // This session is expired.  Remove it from the sessions list.
         UserSession.delete(session);
       }
       else
       {
         retval = true;
       }
       */
       retval = true;
     }  
     return retval;
   }
   
   public static boolean checkAuthToken(String authToken)
   {
     return checkAuthToken(UUID.fromString(authToken.trim()));
   }
}
