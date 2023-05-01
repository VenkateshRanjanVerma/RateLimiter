# RateLimiter

Rate Limiter is implemented for two concerns: Security and Performance.

To prevent from DDoS attack (thousands and lakhs of requests are sent to the backend server in one second, the server is unable to respond, leading to server down. So we'll use Rate Limiter there, putting a condition that in a particular time frame, that client can make a threshold request.

This is a simple implementation of a rate limiter in Java. The RateLimit class keeps track of the rate limit for a given API key and user ID. It uses a sliding window algorithm to check if the number of requests made within a specified window size exceeds the given limit.

# Usage:
