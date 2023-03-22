package com.ecommerce.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.main.model.Role;
import com.ecommerce.main.service.RoleService;
import com.ecommerce.main.util.ApiResponse;

@RestController
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('manage_role')")
	ResponseEntity<ApiResponse> createRoleController(@RequestBody Role role) {
		Integer affectedRow = this.roleService.createRole(role);
		if (affectedRow > 0) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Role created successfully", Boolean.TRUE),
					HttpStatus.CREATED);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!", Boolean.FALSE),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('manage_role')")

	ResponseEntity<ApiResponse> deleteRoleController(@RequestParam("roleId") Integer roleId) {
		Integer affectedRow = this.roleService.deleteRole(roleId);
		if (affectedRow > 0) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Role deleted successfully", Boolean.TRUE),
					HttpStatus.CREATED);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!", Boolean.FALSE),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@PutMapping("/update")
	@PreAuthorize("hasAuthority('manage_role')")

	ResponseEntity<ApiResponse> updateRoleController(@RequestBody Role role) {
		Integer affectedRow = this.roleService.updateRole(role);
		if (affectedRow > 0) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Role updated successfully", Boolean.TRUE),
					HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!", Boolean.FALSE),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@GetMapping("/read")
	@PreAuthorize("hasAuthority('manage_role')")

	ResponseEntity<?> getRole() {
		List<Role> roles = this.roleService.getRole();
		if (!roles.isEmpty()) {
			return new ResponseEntity<>(roles, HttpStatus.FOUND);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!", Boolean.FALSE),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@GetMapping("/readById/{roleId}")
	@PreAuthorize("hasAuthority('manage_role')")

	ResponseEntity<?> getRoleBykey(@PathVariable Integer roleId) {
		Role retrievedRole = this.roleService.getRoleByKey(roleId);
		if (retrievedRole!=null) {
			return new ResponseEntity<>(retrievedRole,
					HttpStatus.FOUND);
		}
		return new ResponseEntity<>(new ApiResponse("something went wrong!!",Boolean.FALSE),
				HttpStatus.NOT_FOUND);

	}

}
