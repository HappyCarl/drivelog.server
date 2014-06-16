
module.exports = function(grunt) {
    grunt.loadNpmTasks('grunt-contrib-coffee');
    grunt.loadNpmTasks('grunt-contrib-watch');

    grunt.initConfig({
        coffee: {
            compile: {
                files: {
                    "target/generated-sources/js/js/main.js": ["src/main/coffee/app.coffee", "src/main/coffee/router.coffee", "src/main/coffee/**/*.coffee"]
                },
                options: {
                    bare: false
                }
            }
        },
        watch: {
            coffee: {
                files: ["src/main/coffee/**/*.coffee"],
                tasks: "coffee"
            }
        }
    });

    grunt.registerTask('install', 'install the backend and frontend dependencies', function() {
        var exec = require('child_process').exec;
        var cb = this.async();
        exec('npm install -g bower', {cwd: './'}, function(err, stdout, stderr) {
            console.log(stdout);
            exec('bower install', {cwd: './'}, function(err, stdout, stderr) {
                console.log(stdout);
                cb();
            });
        });
    });

    grunt.registerTask('build', ['install', 'coffee']);
    grunt.registerTask('dev', ['watch:coffee']);
}