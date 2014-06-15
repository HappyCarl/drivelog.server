
module.exports = function(grunt) {
    grunt.loadNpmTasks('grunt-contrib-coffee');
    grunt.loadNpmTasks('grunt-contrib-watch');

    grunt.initConfig({
        coffee: {
            compile: {
                files: {
                    "target/generated-sources/js/js/main.js": ["src/main/coffee/app.coffee", "src/main/coffee/**/*.coffee"]
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

    grunt.registerTask('build', ['coffee']);
    grunt.registerTask('dev', ['watch:coffee']);
}