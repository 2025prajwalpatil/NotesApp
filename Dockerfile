    # Use Maven + Java 17
FROM maven:3.9.6-eclipse-temurin-17

# Install Chrome + dependencies
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    curl \
    gnupg \
    && wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && apt-get clean

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build project (skip tests first)
RUN mvn clean install -DskipTests

# Run tests
CMD ["mvn", "spring-boot:run"]