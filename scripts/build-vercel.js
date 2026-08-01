const fs = require('fs');
const path = require('path');

const rootDir = path.resolve(__dirname, '..');
const sourceDir = path.join(rootDir, 'src', 'main', 'resources', 'static');
const targetDir = path.join(rootDir, 'dist');

function copyDirectory(source, target) {
  fs.rmSync(target, { recursive: true, force: true });
  fs.mkdirSync(target, { recursive: true });
  fs.cpSync(source, target, { recursive: true });
}

copyDirectory(sourceDir, targetDir);
console.log(`Copied static site from ${sourceDir} to ${targetDir}`);
