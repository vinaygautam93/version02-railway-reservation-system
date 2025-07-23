package com.casestudy.controller;

import java.util.List;
import java.util.Optional;

import com.casestudy.model.AdminModel;
import com.casestudy.model.TrainModel;
import com.casestudy.model.UserModel;
import com.casestudy.service.AdminService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

// @CrossOrigin(origins = "*")

@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	Logger logger = LoggerFactory.getLogger(AdminController.class);

	// ---------- Admin CRUD ----------
	@PostMapping("/registeradmin")
	public String addadmin(@RequestBody AdminModel admin) {
		logger.info("[registeradmin] info message");
		return adminService.addAdmin(admin);
	}

	@GetMapping("/viewadminprofile/{id}")
	public Optional<AdminModel> getadmin(@PathVariable Long id) {
		logger.info("[viewadminprofile] info message");
		return adminService.getAdmin(id);
	}

	@PutMapping("/updateprofile/{id}")
	public String updateadmin(@PathVariable String id, @RequestBody AdminModel adminmodel) {
		logger.info("[updateprofile] info message");
		return adminService.updateAdmin(id, adminmodel);
	}

	@DeleteMapping("/deleteadmin/{id}")
	public String deleteadmin(@PathVariable Long id) {
		logger.info("[deleteadmin] info message");
		return adminService.deleteAdmin(id);
	}

	// ---------- Admin-User ----------
	@GetMapping("/viewallusers")
	public List<UserModel> getallusers() {
		logger.info("[viewallusers] info message");
		return adminService.getAllUsers();
	}

	@GetMapping("/viewuser/{id}")
	public List<UserModel> getuser(@PathVariable String id) {
		logger.info("[viewuser] info message");
		return adminService.getUserById(id);
	}

	@PutMapping("/updateuser/{id}")
	public String updateuser(@RequestBody UserModel usermodel, @PathVariable String id) {
		logger.info("[updateuser] info message");
		return adminService.updateUser(id, usermodel);
	}

	@DeleteMapping("/deleteuser/{id}")
	public String deleteuser(@PathVariable String id) {
		logger.info("[deleteuser] info message");
		return adminService.deleteUser(id);
	}

	// ---------- Admin-Train ----------
	@PostMapping("/addtrain")
	public String addtrain(@RequestBody TrainModel trainmodel) {
		logger.info("[addtrain] info message");
		return adminService.addTrain(trainmodel);
	}

	@GetMapping("/viewalltrains")
	public List<TrainModel> getAllTrains() {
		logger.info("[viewalltrains] info message");
		return adminService.getAllTrains();
	}

	@GetMapping("/viewalltrains/{trainNo}")
	public TrainModel getTrainByNo(@PathVariable String trainNo) {
		logger.info("[viewtrainbyno] info message");
		return adminService.getTrainByNo(trainNo);
	}

	@GetMapping("/viewtrainbyname/{trainName}")
	public TrainModel getTrainByName(@PathVariable String trainName) {
		logger.info("[viewtrainbyname] info message");
		return adminService.getTrainByName(trainName);
	}

	@PutMapping("/updatetrain/{trainNo}")
	public String updatetrain(@RequestBody TrainModel trainmodel, @PathVariable String trainNo) {
		logger.info("[updatetrain] info message");
		return adminService.updateTrain(trainNo, trainmodel);
	}

	@DeleteMapping("/deletetrain/{trainNo}")
	public String deletetrain(@PathVariable String trainNo) {
		logger.info("[deletetrain] info message");
		return adminService.deleteTrain(trainNo);
	}
}