package entities;

import enums.CustomerTier;

public class Customer {
    private long id;
    private String name;
    private CustomerTier tier;

    public Customer(long id, String name, CustomerTier tier) {
        this.id = id;
        this.name = name;
        this.setTier(tier);
    }

    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }
    

    public CustomerTier getTier() {
        return tier;
    }

    public void setTier(CustomerTier tier) {
        this.tier = tier;
    }
}
