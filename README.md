# rest-retry-application
- It's Spring boot REST service.
- It provides two different types of retry mechanism i.e. RetryResilience and RetryTemplate.
- It provides rest endpoints to receive the consumer request.
  - When it receives the request, it tries to send the request to the server application.
  - Here, expecting that, server application is down or response.
  - Now, request will be failed to send, and it is eligible for a retry.
- It logs the information and send the response server's response back to caller.