require('dotenv').config();

module.exports = {
  "dev": {
    "default-src": "'self'",
    "object-src": "'none'",
    "script-src": ["'self'"],
    "style-src": ["'self'", "'unsafe-inline'"],
    "img-src": ["'self'", "blob:"],
    "connect-src": ["'self'", `${process.env.RESOURCE_SERVER_DOMAIN}`]
  },
  "prod": {
    "default-src": "'none'"
  }
}
