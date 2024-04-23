const path = require('path');


module.exports = {
    mode: 'development', // Or 'production'
    // ... other configurations
    entry: './src/app.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist'),
    },
    module: {
        rules: [
            {
                test: /\.html$/i,
                use: {
                    loader: 'html-loader',
                },
            },
            {
                test: /\.(png|jpe?g|gif)$/i, // Matches image file extensions
                use: [
                    {
                        loader: 'file-loader',
                    },
                ],
            },
        ],
    },
    devServer: {
        contentBase: path.resolve(__dirname, 'dist'),
        open: true,
        clientLogLevel: 'silent',
        port: 9000
      },
    
    
};
