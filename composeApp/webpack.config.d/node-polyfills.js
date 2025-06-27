// composeApp/webpack.config.d/node-polyfills.js
const webpack = require("webpack");

module.exports = (config, env) => {
  // Configure fallback pour Node core modules
  config.resolve = config.resolve || {};
  config.resolve.fallback = {
    path: require.resolve("path-browserify"),
    fs: false,
    crypto: require.resolve("crypto-browserify"),
  };

  // Fournit un global `process` pour les modules qui l'attendent
  config.plugins = config.plugins || [];
  config.plugins.push(
    new webpack.ProvidePlugin({
      process: "process/browser",
    })
  );

  return config;
};
