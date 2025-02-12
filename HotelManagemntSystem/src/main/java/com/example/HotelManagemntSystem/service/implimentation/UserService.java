package com.example.HotelManagemntSystem.service.implimentation;

import com.example.HotelManagemntSystem.dao.LoginRequest;
import com.example.HotelManagemntSystem.dao.Response;
import com.example.HotelManagemntSystem.dao.UserDTO;
import com.example.HotelManagemntSystem.exception.OurException;
import com.example.HotelManagemntSystem.models.User;
import com.example.HotelManagemntSystem.repository.UserRepository;
import com.example.HotelManagemntSystem.service.interfaces.UserInterfaceService;
import com.example.HotelManagemntSystem.utils.JWTUtils;
import com.example.HotelManagemntSystem.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService implements UserInterfaceService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;



    @Override
    public Response registre(User user) {
        Response response = new Response();
        try {
             if(user.getRole()==null || user.getRole().isBlank() ){
                 user.setRole("USER");
             }

             if (userRepository.existsByEmail(user.getEmail())) {
                 throw new OurException("User with email " + user.getEmail() + " already exists!");
             }
             user.setPassword(passwordEncoder.encode(user.getPassword()));
             User savedUser = userRepository.save(user);
             UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);
             System.out.println(userDTO+" userDTO");
             response.setUser(userDTO);
             response.setStatusCode(200);
        } catch (OurException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(400);
        }
        catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("user Not found"));
            var token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");
            response.setMessage("Login successful");
         }
        catch (OurException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
        }
        catch (Exception e) {
            response.setMessage("Error Occurred During USer Login "+e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }
    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try {
            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(users);
            response.setStatusCode(200);
            response.setMessage("Successfully Fetched All Users");
            response.setUserList(userDTOList);
        } catch (Exception e) {
            response.setMessage("Error Occurred While Fetching All Users "+e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        Response response = new Response();
        try {
            User user=userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO=Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
            response.setStatusCode(200);
            response.setMessage("Successfully Fetched User Booking History");
            response.setUser(userDTO);
        } catch (OurException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
        } catch (Exception e) {
            response.setMessage("Error Occurred While Fetching User Booking History "+e.getMessage());
            response.setStatusCode(500);
        }


        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();
        try {
            User user=userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new OurException("User Not Found"));
            userRepository.delete(user);
            response.setStatusCode(200);
            response.setMessage("Successfully Deleted User");
        } catch (OurException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
        } catch (Exception e) {
            response.setMessage("Error Occurred While Deleting User "+e.getMessage());
            response.setStatusCode(500);
        }


        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();
        try {
            User user=userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO=Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Successfully Fetched User");
            response.setUser(userDTO);
        } catch (OurException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
        } catch (Exception e) {
            response.setMessage("Error Occurred While Fetching User "+e.getMessage());
            response.setStatusCode(500);
        }

        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();
        try {
            User user=userRepository.findByEmail(email).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO=Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Successfully Fetched User");
            response.setUser(userDTO);
        } catch (OurException e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
        } catch (Exception e) {
            response.setMessage("Error Occurred While Fetching User "+e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }
}
