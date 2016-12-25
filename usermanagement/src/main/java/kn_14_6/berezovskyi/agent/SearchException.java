package kn_14_6.berezovskyi.agent;

public class SearchException extends Exception {

    private static final long serialVersionUID = 1L;
    
   
   public SearchException(String e) {
       super(e);
   }
   
   public SearchException() {
       super();
   }

   public SearchException(Throwable cause) {
       super(cause);
   }

}
