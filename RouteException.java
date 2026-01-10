// File: RouteException.java

public class RouteException extends Exception {  // <--- This 'extends' part is critical
    public RouteException(String message) {
        super(message);
    }
}