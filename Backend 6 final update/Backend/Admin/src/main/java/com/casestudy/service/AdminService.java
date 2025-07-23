package com.casestudy.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.casestudy.model.AdminModel;
import com.casestudy.model.TrainModel;
import com.casestudy.model.UserModel;
import com.casestudy.repository.AdminRepository;
import com.casestudy.repository.TrainRepository;
import com.casestudy.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AdminRepository adminrepo;

    @Autowired
    private TrainRepository trainrepo;

    @Autowired
    private UserRepository userrepo;

    // Methods mirroring controller logic
    public String addAdmin(AdminModel admin) {
        adminrepo.save(admin);
        return "Admin with Id: " + admin.getId() + " have been Registered Successfully";
    }

    public Optional<AdminModel> getAdmin(Long id) {
        return adminrepo.findById(id);
    }

    public String updateAdmin(String id, AdminModel adminmodel) {
        adminrepo.save(adminmodel);
        return "Admin with id " + id + " have been updated successfully";
    }

    public String deleteAdmin(Long id) {
        adminrepo.deleteById(id);
        return "Admin with id " + id + " have been deleted";
    }

    public List<UserModel> getAllUsers() {
        return Arrays.asList(restTemplate.getForObject("http://UserDetails/user/viewallusers", UserModel[].class));
    }

    public List<UserModel> getUserById(String id) {
        return Arrays.asList(restTemplate.getForObject("http://UserDetails/user/viewuser/" + id, UserModel[].class));
    }

    public String updateUser(String id, UserModel usermodel) {
        restTemplate.put("http://UserDetails/user/updateprofile/{id}", usermodel, id);
        return "User with id : " + id + " have been updated";
    }

    public String deleteUser(String id) {
        restTemplate.delete("http://UserDetails/user/deleteprofile/{id}", id);
        return "User with id :" + id + " have been deleted";
    }

    public String addTrain(TrainModel trainmodel) {
        restTemplate.postForObject("http://TrainDetails/train/addtrain", trainmodel, TrainModel.class);
        return "Train with No: " + trainmodel.getTrainNo() + " have been added Successfully";
    }
//public String addTrain(TrainModel trainmodel) {
//    String response = restTemplate.postForObject(
//            "http://TrainDetails/train/addtrain",
//            trainmodel,
//            TrainModel.class
//    );
//    return "Train with No: " + trainmodel.getTrainNo() + " has been added successfully. Response: " + response;
//}

    public List<TrainModel> getAllTrains() {
        return Arrays.asList(restTemplate.getForObject("http://TrainDetails/train/viewalltrains", TrainModel[].class));
    }

    public TrainModel getTrainByNo(String trainNo) {
        return restTemplate.getForObject("http://TrainDetails/train/viewtrainbyno/" + trainNo, TrainModel.class);
    }

    public TrainModel getTrainByName(String trainName) {
        return restTemplate.getForObject("http://TrainDetails/train/viewtrainbyname/" + trainName, TrainModel.class);
    }

    public String updateTrain(String trainNo, TrainModel trainmodel) {
        restTemplate.put("http://TrainDetails/train/updatetrain/{trainNo}", trainmodel, trainNo);
        return "Train with no. : " + trainNo + " have been updated";
    }

    public String deleteTrain(String trainNo) {
        restTemplate.delete("http://TrainDetails/train/deletetrain/" + trainNo);
        return "Train with no. :" + trainNo + " have been deleted";
    }
}