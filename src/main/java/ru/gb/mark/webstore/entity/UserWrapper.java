package ru.gb.mark.webstore.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

//Builder pattern
@Data
public class UserWrapper {

    public static Builder builder() {
        return new Builder();
    }


    @Data
    public static class Builder {

        private User user;

        private Builder() {
            this.user = new User();
        }

        public Builder setFirstname(String firstname) {
            this.user.setFirstname(firstname);
            return this;
        }

        public Builder setLastname(String lastname) {
            this.user.setLastname(lastname);
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.user.setDate(date);
            return this;
        }

        public Builder setPassword(String password) {
            this.user.setPassword(password);
            return this;
        }

        public Builder setEmail(String email) {
            this.user.setEmail(email);
            return this;
        }

        public Builder setPhone(String phone) {
            this.user.setPhone(phone);
            return this;
        }

        public Builder setRoles(List<Role> roles) {
            this.user.setRoles(roles);
            return this;
        }

        public Builder setCart(Cart cart) {
            this.user.setCart(cart);
            return this;
        }

        public User build() {
            return this.user;
        }
    }
}
