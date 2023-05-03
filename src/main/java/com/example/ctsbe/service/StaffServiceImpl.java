package com.example.ctsbe.service;

import com.example.ctsbe.dto.group.GroupRemoveStaffDTO;
import com.example.ctsbe.dto.staff.StaffAddDTO;
import com.example.ctsbe.dto.staff.StaffAvailableDTO;
import com.example.ctsbe.dto.staff.StaffUpdateDTO;
import com.example.ctsbe.entity.Account;
import com.example.ctsbe.entity.PromotionLevel;
import com.example.ctsbe.entity.Staff;
import com.example.ctsbe.repository.AccountRepository;
import com.example.ctsbe.repository.PromotionLevelRepository;
import com.example.ctsbe.repository.RoleRepository;
import com.example.ctsbe.repository.StaffRepository;
import com.example.ctsbe.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PromotionLevelRepository promotionLevelRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Staff addStaff(StaffAddDTO staffAddDTO, int roleId) {
        Staff staff = convertStaffAddDTOToStaff(staffAddDTO);
        staff.setPromotionLevel(promotionLevelRepository.findById(roleId == 1 ? 13 : roleId == 2 ? 10 : 1));
        return staffRepository.save(staff);
    }

    @Override
    public Page<Staff> getAllStaff(Pageable pageable) {
        return staffRepository.getListStaffExceptAdmin(pageable);
    }

    @Override
    public Page<Staff> getStaffByName(String name, Pageable pageable) {
        return staffRepository.getListStaffByName(name, pageable);
    }

    @Override
    public Staff findStaffByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    @Override
    public void changePromotionLevel(StaffUpdateDTO dto) {
        Account account = accountRepository.getById(dto.getStaffId());
        Staff staff = account.getStaff();
        staff.setPromotionLevel(promotionLevelRepository.getById(dto.getLevelId()));
        account.setRole(roleRepository.getById(dto.getRoleId()));
        account.setLastUpdated(Instant.now());
        staff.setLastUpdated(Instant.now());
        accountRepository.save(account);
        staffRepository.save(staff);
    }

    @Override
    public List<Staff> getListAvailableStaff(int groupId, int projectId) {
        return staffRepository.getAvailableStaffAddToProject(groupId, projectId);
    }

    @Override
    public List<Staff> getStaffsByRole(int role) {
        List<Staff> staffList = new ArrayList<>();
        List<Account> accountList = accountRepository.getAccByRole(role);
        for (Account acc : accountList) {
            staffList.add(acc.getStaff());
        }
        return staffList;
    }

    @Override
    public List<Staff> getListStaffAddToGroup() {
        return staffRepository.getListStaffAvailableAddToGroup();
    }

    @Override
    public List<Staff> getListPMAvailable() {
        return staffRepository.getListPMAvailable();
    }

    @Override
    public List<Staff> getListGLAvailable() {
        return staffRepository.getListGroupLeaderAvailable();
    }

    @Override
    public Page<Staff> getListStaffByEnable(byte enable, Pageable pageable) {
        return staffRepository.getListStaffByEnable(enable, pageable);
    }

    @Override
    public Page<Staff> getListStaffByNameAndEnable(String name, byte enable, Pageable pageable) {
        return staffRepository.getListStaffByNameAndEnable(name, enable, pageable);
    }

    @Override
    public List<Staff> getListPMInGroup(int groupId) {
        return staffRepository.getListPMInGroup(groupId);
    }

    @Override
    public void setStaffToPM(int staffId) {
        Account account = accountRepository.getById(staffId);
        account.setRole(roleRepository.getById(3));
        accountRepository.save(account);
    }

    @Override
    public List<Staff> getListStaffForTimeSheet(int staffId, int prjId) {
        Account account = accountRepository.getById(staffId);
        List<Staff> empty = new ArrayList<>();
        int role = account.getRole().getId();
        if (role == 2 && prjId == 0) {
            return staffRepository.getListStaff();
        } else if (role == 4 && prjId == 0) {
            return staffRepository.getListStaffByGroup(account.getStaff().getGroup().getId());
        } else if (role == 3) {
            if (prjId == 0) return empty;
            else return staffRepository.getListStaffInProject(prjId);
        }
        return null;
    }

    @Override
    public boolean checkStaffInRemoveFromGroup(GroupRemoveStaffDTO dto) {
        List<Integer> listStaffId = dto.getStaffId();
        boolean check = true;
        for (int i = 0; i < listStaffId.size(); i++) {
            Staff existedStaff = staffRepository.getById(listStaffId.get(i));
            List<Staff> listStaffCheck = staffRepository.checkStaffInRemoveFromGroup(existedStaff.getGroup().getId());
            //neu check = true -> nhan vien dang o trong 1 project processing -> khong duoc xoa
            if (listStaffCheck.stream().anyMatch(existedStaff::equals) == true) {
                check = true;
                break;
            }
            //neu check = fale -> nhan vien khong o trong project nao processing -> duoc phep xoa
            else check = false;
        }
        return check;
    }

    @Override
    public Staff getStaffById(int staffId) {
        return staffRepository.findStaffById(staffId);
    }

    @Override
    public boolean checkStaffInProjectProcessing(Account account) {
        boolean check = true;
        List<Staff> list = staffRepository.checkStaffInProjectProcessing();
        //neu check = true -> nhan vien dang o trong 1 project processing -> khong duoc xoa
        if (list.stream().anyMatch(account.getStaff()::equals) == true) {
            check = true;
        }
        //neu check = fale -> nhan vien khong o trong project nao processing -> duoc phep xoa
        else check = false;
        return check;
    }

    @Override
    public Page<Staff> getListStaffByGroup(int groupId, Pageable pageable) {
        return staffRepository.getListMemberByGroup(groupId, pageable);
    }


    public Staff convertStaffAddDTOToStaff(StaffAddDTO dto) {
        DateUtil dateUtil = new DateUtil();
        Staff staff = new Staff();
        staff.setEmail(dto.getEmail());
        staff.setFirstName(dto.getFirstName());
        staff.setSurname(dto.getSurname());
        staff.setCreatedDate(Instant.now());
        staff.setLastUpdated(Instant.now());
        staff.setDateOfBirth(dateUtil.convertStringToLocalDate(dto.getDateOfBirth()));
        staff.setPhone(dto.getPhone());
        return staff;
    }
}
