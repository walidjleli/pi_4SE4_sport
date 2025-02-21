package tn.esprit.sporty.Controller;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tn.esprit.sporty.Repository.UserRepository;
import tn.esprit.sporty.security.JwtUtil;
import tn.esprit.sporty.Service.EmailServiceImpl;
import tn.esprit.sporty.Service.ResetPasswordService;
import tn.esprit.sporty.Service.CustomUserDetailsService;
import tn.esprit.sporty.Entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/auth")
@CrossOrigin("*")
public class AuthController {

    private EmailRequest emailRequest;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private EmailServiceImpl emailServiceImpl;
    private final UserRepository userRepository;


    @Autowired
    private CustomUserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;

    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginReq loginReq) {


        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            String email = authentication.getName();


            User user = new User(email, "");
            String token = jwtUtil.createToken(user);
            System.out.println("token generated: " + token);
            LoginRes loginRes = new LoginRes(email, token);

            return ResponseEntity.ok(loginRes);

        } catch (BadCredentialsException e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            System.out.println(e);
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/register")
    @CrossOrigin("*")
    public ResponseEntity registerUser(@RequestBody User user) {
        try {
            // Vérifier si le rôle nécessite une activation
            if ("DOCTOR".equalsIgnoreCase(String.valueOf(user.getRole()))
                    || "COACH".equalsIgnoreCase(String.valueOf(user.getRole()))) {
                user.setActivationStatus(Status.OFF); // Nécessite l'activation par un admin
            } else {
                user.setActivationStatus(Status.ON);  // Les autres rôles sont activés directement
            }

            // Générer un token d'activation si nécessaire
            if(user.getActivationStatus() == Status.OFF) {
                String token = UUID.randomUUID().toString();
                user.setActivationToken(token);
            }

            userDetailsService.createUser(user);

            // Si activation nécessaire, envoyer un email
            if(user.getActivationStatus() == Status.OFF) {
                emailServiceImpl.sendActivationEmail(user);
            }

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la création de l'utilisateur.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<User>> getUser(@PathVariable Long userId) {
        User user = userDetailsService.getUserById(userId);
        List<User> userList = new ArrayList<>();
        if (user != null) {
            userList.add(user);
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/user/role")
    public ResponseEntity<UserRoleResponse> getUserRoleByEmail(@RequestBody EmailRequest emailRequest) {
        String email = emailRequest.getEmail();
        try {
            System.out.println("Received request to get user role for email: " + email);

            String role = String.valueOf(userDetailsService.getUserRoleByEmail(email));
            if (role != null) {
                System.out.println("User role found: " + role);
                UserRoleResponse response = new UserRoleResponse(role);
                return ResponseEntity.ok(response);
            } else {
                System.out.println("User role not found for email: " + email);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("An error occurred while fetching user role for email: " + email);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/admin/authorize/{userId}")
    public ResponseEntity<String> authorizeUser(@PathVariable Long userId) {
        boolean updated = userDetailsService.updateUserActivationStatus(userId, Status.ON);

        if (updated) {
            return ResponseEntity.ok("Utilisateur activé avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userDetailsService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the user.");
        }
    }

    //////
    @PostMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam("email") String email,
                                                  @RequestParam("token") String token) {
        try {
            System.out.println("Activation request for email: " + email + " with token: " + token);

            User user = userDetailsService.findByEmail(email);
            if(user == null) {
                System.out.println("User not found for email: " + email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable.");
            }

            System.out.println("Stored activation token: " + user.getActivationToken());

            if(user.getActivationToken() != null && user.getActivationToken().equals(token)) {
                // Activer le compte
                user.setActivationStatus(Status.ON);
                user.setActivationToken(null);

                userRepository.save(user);
                return ResponseEntity.ok("Compte activé avec succès.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token d'activation invalide.");
            }
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'activation du compte.");
        }
    }

    @Autowired
    private ResetPasswordService resetPasswordService;

    // Endpoint pour demander un reset de mot de passe
    @PostMapping("/request-reset")
    public ResponseEntity<String> requestResetPassword(@RequestParam String email) {
        boolean result = resetPasswordService.sendResetPasswordToken(email);
        if (result) {
            return ResponseEntity.ok("Email de réinitialisation envoyé avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable.");
        }
    }

    // Endpoint pour réinitialiser le mot de passe
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean result = resetPasswordService.resetPassword(token, newPassword);
        if (result) {
            return ResponseEntity.ok("Mot de passe réinitialisé avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token invalide ou expiré.");
        }
    }




}