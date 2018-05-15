/**
 * Created by Anton on 15.05.2018.
 */
public class GetAnswer {
    String getAnswer(int code) {
        switch (code) {
            case 200: {
                System.out.println("200 OK");

                return "OK";
            }
            case 401:
            {
                System.out.println("401 Unathorized");
                return "Unathorized";
            }
            case 451:
            {
                System.out.println("451 Unavailable For Legal Reasons");
                return "Unavailable For Legal Reasons";
            }
            case 404:
            {
                System.out.println("404 Not Found");
                return "Not Found";
            }

            case 405:
            {
                System.out.println("405 Method Not Allowed");
                return "Method Not Allowed";
            }
            case 403:
            {
                System.out.println("404 Forbidden");
                return "Forbidden";
            }

            case 411:
            {
                System.out.println("411 Length Required");
                return "Length Required";
            }

            case 501:
            {
                System.out.println("501 Not Implemented");
                return "Not Implemented";
            }

            case 400:
            {
                System.out.println("Bad request");
                return "Bad request";
            }

            default:
                return "Internal Server Error";
        }

    }
}
