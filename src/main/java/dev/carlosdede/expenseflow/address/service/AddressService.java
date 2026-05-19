package dev.carlosdede.expenseflow.address.service;

import dev.carlosdede.expenseflow.address.repository.AddressRepository;
import org.springframework.stereotype.Service;


@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }


}
