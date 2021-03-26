package stud.NTNU.idatt2001.Oblig3.Cardgame.exception;

public class WrongInputException extends  Exception{

        //serialVersionUID declaration
        public static final long serialVersionUID=1L;

        //constructor
        public WrongInputException(String message) {
            super(message);
        }

}
