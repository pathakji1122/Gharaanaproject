package com.beginnner.gharaana.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

    @Document("order")
    public class Order {

        @Id
        private final String orderId;
        private String email;
        private String name;
        private Location location;
        private Expertise expertise;
        private Integer price;
        private OrderStatus orderStatus;
        private String expert;
        private Times times;
        private Boolean payment;
        private String paymentId;


        public Order(String email, String name, Location location, Expertise expertise, Integer price, OrderStatus orderStatus, String orderId, String expert, Times times, Boolean payment, String paymentId) {
            this.email = email;
            this.name = name;
            this.location = location;
            this.expertise = expertise;
            this.price = price;
            this.orderStatus = orderStatus;
            this.orderId = orderId;
            this.expert = expert;
            this.times = times;
            this.payment= payment;
            this.paymentId=paymentId;
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }
        public Boolean getPayment() {
            return payment;
        }

        public void setPayment(Boolean payment) {
            this.payment = payment;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Expertise getExpertise() {
            return expertise;
        }

        public void setExpertise(Expertise expertise) {
            this.expertise = expertise;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public OrderStatus getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getExpert() {
            return expert;
        }

        public void setExpert(String expert) {
            this.expert = expert;
        }

        public Times getTimes() {
            return times;
        }

        public void setTimes(Times times) {
            this.times = times;
        }

        public static class OrderBuilder {
            private final String orderId;
            private String email;
            private String name;
            private Location location;
            private Expertise expertise;
            private Integer price;
            private OrderStatus orderStatus;
            private String expert;
            private Times times;

            private Boolean payment;
            private String paymentId;
            public OrderBuilder(String email, String name, Location location, Expertise expertise, Integer price, OrderStatus orderStatus, String orderId, String expert, Times times,Boolean payment,String paymentId) {
                this.email = email;
                this.name = name;
                this.location = location;
                this.expertise = expertise;
                this.price = price;
                this.orderStatus = orderStatus;
                this.orderId = orderId;
                this.expert = expert;
                this.times = times;
                this.payment=payment;
                this.paymentId=paymentId;

            }

            public OrderBuilder(String orderId) {
                this.orderId = orderId;
            }


            public OrderBuilder setEmail(String email) {
                this.email = email;
                return this;
            }

            public OrderBuilder setName(String name) {
                this.name = name;
                return this;
            }

            public OrderBuilder setLocation(Location location) {
                this.location = location;
                return this;
            }

            public OrderBuilder setExpertise(Expertise expertise) {
                this.expertise = expertise;
                return this;
            }

            public OrderBuilder setPrice(Integer price) {
                this.price = price;
                return this;
            }

            public OrderBuilder setOrderStatus(OrderStatus orderStatus) {
                this.orderStatus = orderStatus;
                return this;
            }


            public OrderBuilder setExpert(String expert) {
                this.expert = expert;
                return this;
            }
            public OrderBuilder setPayment(Boolean payment) {
                this.payment = payment;
                return this;
            }
            public OrderBuilder setPaymentId(String paymentId) {
                this.paymentId = paymentId;
                return this;
            }

            public OrderBuilder setTimes(Times times) {
                this.times = times;
                return this;
            }

            public com.beginnner.gharaana.Entity.Order build() {
                return new com.beginnner.gharaana.Entity.Order(email, name, location, expertise, price, orderStatus, orderId, expert, times,payment,paymentId);
            }
        }
    }



