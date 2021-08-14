require('dotenv').config();

module.exports = {
  "dev": {
    "default-src": "'self'",
    "object-src": "'none'",
    "script-src": ["'self'", "'unsafe-inline'"],
    "style-src": ["'self'", "'unsafe-inline'"],
    "img-src": ["'self'", "data:", "blob:"],
    "connect-src": ["'self'", `${process.env.RESOURCE_SERVER_DOMAIN}`]
  },
  "prod": {
    "default-src": "'self'",
    "object-src": "'none'",
    "script-src": ["'self'"],
    "style-src": ["'self'", "'unsafe-inline'"],
    "img-src": ["'self'", "data:", "blob:"],
    "connect-src": ["'self'", `${process.env.RESOURCE_SERVER_DOMAIN}`]
  }
}
