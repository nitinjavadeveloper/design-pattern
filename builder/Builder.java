package builder;

public class Builder {
    private String name;
    private String email;
    private String number;

    public Builder(User user) {
        this.name = user.name;
        this.email = user.email;
        this.number = user.number;

    }

    static class User {
        private String name;
        private String email;
        private String number;
       
        public User setName(String name) {
            this.name = name;
            return this;
        }

        public User setEmail(String email) {
            this.email = email;
            return this;
        }
        public User setNumber(String number) {
            this.number = number;
            return this;
        }
        public Builder build() {
            return new Builder(this);
        }
    }

    public static void main(String[] args) {
        /**
         * Represents a user with configurable properties using the Builder pattern.
         * <p>
         * Example usage:
         * <pre>
         *     Builder.User user = new Builder.User()
         *         .setName("Alice")
         *         .setEmail("alice@example.com")
         *         .setNumber("1234567890");
         * </pre>
         * </p>
         *
         * @see Builder
         */
        Builder.User user = new Builder.User()
            .setName("Alice")
            .setEmail("alice@example.com")
            .setNumber("1234567890");
        Builder builder = user.build();
        System.out.println("Name: " + builder.name);
    }
}
