package com.Project1.Car.Insurance.System.services;

import com.Project1.Car.Insurance.System.dtos.PolicyRequest;
import com.Project1.Car.Insurance.System.dtos.PolicyResponse;
import com.Project1.Car.Insurance.System.entities.*;
import com.Project1.Car.Insurance.System.mappers.PolicyMapper;
import com.Project1.Car.Insurance.System.repositories.ClientRepository;
import com.Project1.Car.Insurance.System.repositories.PolicyRepository;
import com.Project1.Car.Insurance.System.repositories.VehicleRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PolicyService {
    private final PolicyRepository policyRepository;
    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;
    private final PolicyMapper policyMapper;


    public PolicyResponse addPolicy(PolicyRequest policyRequest, String email){

        validateClient(email,policyRequest.vehicleId());

        Client client = clientRepository.getClientByEmail(email);
        Vehicle vehicle = vehicleRepository.findByVehicleId(policyRequest.vehicleId());
        if(!vehicle.getClient().getEmail().equals(email))
            throw new IllegalStateException("Forbidden Access");
        boolean isVehicleFound  = false;

        for(Vehicle i : client.getVehicles()) {
            if (i.getVehicleId().equals(vehicle.getVehicleId())){
                isVehicleFound = true;
                break;
            }
        }

        if(!isVehicleFound)
            throw new IllegalStateException("Client: "+client.getClientId()+ " does not own Vehicle:" +vehicle.getVehicleId());

        if(policyRepository.existsPolicyByVehicle_VehicleId(policyRequest.vehicleId())) throw new IllegalStateException("vehicle already has policy");
        LocalDate now = LocalDate.now();
        Double premiumAmount = calculatePremium(vehicle.getYear(),client.getDateOfBirth(),policyRequest.policyType());
        Policy policy = Policy
                .builder()
                .vehicle(vehicle)
                .client(client)
                .policyType(policyRequest.policyType())
                .startDate(now)
                .endDate(now.plusMonths(1))
                .policyStatus(PolicyStatus.PENDING)
                .premiumAmount(premiumAmount)
                .build();

        client.getPolicies().add(policy);


        return policyMapper
                .toPolicyResponse(policyRepository.save(policy)
                );
    }

    private Double calculatePremium(Integer carYear, LocalDate dateOfBirth, @NotNull PolicyType policyType) {
        double policyPrice = getPolicyPrice(policyType);

        double carAgePrice = getCarAgePrice(carYear);

        double driverAgePrice = getDriverAgePrice(dateOfBirth);

        double premium = policyPrice * carAgePrice * driverAgePrice;

        return Math.round(premium * 100.0) /100.0;
    }

    private double getDriverAgePrice(LocalDate dateOfBirth) {
        int age = LocalDate.now().getYear() - dateOfBirth.getYear();

        if(age < 21)
            return 1.5;
        else if( age < 61)
            return 1.2;
        else
            return 1.5;

    }

    private double getCarAgePrice(Integer carYear) {
        int currentYear = LocalDate.now().getYear();
        int age = currentYear - carYear;

        if(age < 3)
            return 0.9;
        else if(age < 8)
            return 1;
        else
            return 1.5;
    }

    private double getPolicyPrice(@NotNull PolicyType policyType) {
        if(policyType.equals(PolicyType.THIRD_PARTY))
          return 500;
        else if (policyType.equals(PolicyType.COMPREHENSIVE))
            return 1200;
        else
            throw new IllegalStateException("Policy Type: " + policyType + " is not available");
    }


    private void validateClient(String email,UUID vehicleId){
        if(!clientRepository.existsClientByEmail(email))
            throw new IllegalStateException("client does not exist");
        if(!clientRepository.getClientByEmail(email).isProfileCompleted())
            throw new IllegalStateException("complete profile to continue");
        if(!vehicleRepository.existsById(vehicleId))
            throw new IllegalStateException("vehicle does not exist");

    }

    public PolicyResponse getPolicy(UUID policyId, String email) {
        checkIfClientExists(email);
        Policy policy = validatePolicy(policyId,email);
        return policyMapper.toPolicyResponse(policy);
    }

    private Policy validatePolicy(UUID  policyId,String email){
        Policy policy = policyRepository.findPoliciesByPolicyId(policyId);
        if(policy == null) throw new IllegalStateException("Policy not found");
        if(!policy.getClient().getEmail().equals(email)) throw new IllegalStateException("Forbidden access");

        return policy;
    }
    private void checkIfClientExists(String email){
        if(!clientRepository.existsClientByEmail(email))
            throw new IllegalStateException("client does not exist");
    }

    public void cancelPolicy(UUID policyId, String email) {
        changePolicyStatus(policyId,email,PolicyStatus.CANCELLED);
    }public void renewPolicy(UUID policyId, String email) {
        changePolicyStatus(policyId,email,PolicyStatus.ACTIVE);
    }
    private void changePolicyStatus(UUID policyId,String email, PolicyStatus policyStatus){
        Policy policy = validatePolicy(policyId,email);
        policy.setPolicyStatus(policyStatus);
        policyRepository.save(policy);
    }

    public List<PolicyResponse> getExpiredPolicies(String email) {
        checkIfClientExists(email);
        return clientRepository
                .getClientByEmail(email)
                .getPolicies()
                .stream()
                .filter(
                        policy ->
                                checkPolicyExpiry(policy.getEndDate())
                )
                .map(policyMapper::toPolicyResponse)
                .toList();
    }
    private boolean checkPolicyExpiry(LocalDate expiryDate){
        LocalDate localDate = LocalDate.now();
        return localDate.isAfter(expiryDate);
    }
}
