
module.exports = function(grunt) {
    var targetDir = grunt.option("target");

    grunt.loadNpmTasks('grunt-contrib-coffee');

    grunt.initConfig({
        coffee: {
            compile: {
                files: {
                    "<%= grunt.option('target') %>/js/main.js": ["src/main/coffee/app.coffee", "src/main/coffee/**/*.coffee"]
                }
            }
        }
    });

    grunt.registerTask('build', ['coffee']);
}