package practice;

import java.util.*;

// This class represents a
// directed graph using adjacency list
// representation
class App {
    static Integer balance = 23;
    static String secretKey = "prateek";
    static HashSet<String> DB = new HashSet<>();

    public static void main(String[] args) {
        System.out.println(generateOrderId());
        System.out.println(generateOrderId());
        System.out.println(generateOrderId());
        System.out.println(generateOrderId());

    }


    public static GetDataResponse getData(GetDataRequest getDataRequest) {
        String token = getDataRequest.token;
        boolean verification = verify(token); //service
        String email = getUserViaToken(token);
        if (verification) {
            return new GetDataResponse(balance, true);
        } else {
            return new GetDataResponse(null, false);
        }
    }

    public static SignupResponse signUp(SignupRequest signupRequest) {
        boolean success = true; //service
        DB.add(signupRequest.email);
        if (success) {
            return new SignupResponse(true);
        } else {
            return new SignupResponse(false);
        }
    }

    public static LoginResponse login(LoginRequest loginRequest) {
        boolean success = true; //service
        String email = loginRequest.email;
        if (success == false) {
            return new LoginResponse(false, null);
        } else {
            String token = generateToken(email); //service
            return new LoginResponse(true, token);
        }
    }

    public static String generateToken(String email) {
        return email + "___" + secretKey;
    }

    public static boolean verify(String token) {
        if (token.split("___").length != 2) {
            return false;
        }
        String email = token.split("___")[0];
        String secret = token.split("___")[1];
        if (!secret.equals(secretKey)) {
            return false;
        }
        if (!DB.contains(email)) {
            return false;
        }
        return true;
    }

    public static String getUserViaToken(String token) {
        return token.split("___")[0];
    }

    public static String generateOrderId() {
        return new String(new Random().nextInt() + "" + new Random().nextInt());
    }


}

class GetDataRequest {
    public GetDataRequest(String token) {
        this.token = token;
    }

    String token;
}

class GetDataResponse {
    Integer data;
    boolean success;

    public GetDataResponse(Integer data, boolean success) {
        this.data = data;
        this.success = success;
    }
}

class LoginRequest {
    String email;
    String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }


}

class LoginResponse {
    public LoginResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    boolean success;
    String token;
}

class SignupRequest {
    String name;
    boolean isWorker;

    public SignupRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    String email;
    String password;
}

class SignupResponse {
    boolean success;

    public SignupResponse(boolean success) {
        this.success = success;
    }
}
