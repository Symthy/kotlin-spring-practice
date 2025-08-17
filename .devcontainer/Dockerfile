FROM mcr.microsoft.com/devcontainers/java:21

# Install additional tools
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends \
        curl \
        wget \
        git \
        vim \
        htop \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Set JAVA_HOME
ENV JAVA_HOME=/usr/local/openjdk-21
ENV PATH="$JAVA_HOME/bin:$PATH"

# Verify Java installation
RUN java -version && javac -version

# Create workspace directory
WORKDIR /workspaces/kotlin-spring-practice

# Copy gradle wrapper files first for better caching
COPY gradlew ./
COPY gradle/ ./gradle/

# Make gradlew executable
RUN chmod +x ./gradlew

# Set the user
USER vscode
