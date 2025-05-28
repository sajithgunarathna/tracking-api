# Full Tracking API

## Requirements Handled
- Accepts full metadata: origin, destination, weight, timestamp, customer details
- Generates regex-compliant tracking number (^[A-Z0-9]{1,16}$)
- Uses Redis for distributed uniqueness
- Handles concurrent requests safely

## Run

```bash
docker run -d -p 6379:6379 redis
mvn spring-boot:run
```

## Test

```bash
curl -X GET "http://localhost:8080/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2018-11-20T19:29:32%2B08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics"
```