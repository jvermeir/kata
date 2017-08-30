describe("PhotoSorter", function () {
    var PhotoSorter = require('../../src/PhotoSorter.js');

    describe("PhotoSorter", function () {
        it("should parse date from fileName", function () {
            photoSorter = new PhotoSorter()
            expect(photoSorter.parseDateFromFileName('20170101_123456')).toEqual('201701');
            expect(photoSorter.parseDateFromFileName('IMG-20170101_123456')).toEqual('201701');
            expect(photoSorter.parseDateFromFileName('input/data/20170101_123456')).toEqual('201701');
            expect(photoSorter.parseDateFromFileName('IM-20170101_123456')).toEqual('');
            expect(photoSorter.parseDateFromFileName('aap')).toEqual('');
            expect(photoSorter.parseDateFromFileName('')).toEqual('');
        }),
            it("should find 5 jpg files in testData/input", function () {
                expect(photoSorter.getListOfJpgFiles('../validate/testData/input').length).toEqual(5);
            }),
            it("should copy 3 jpg files in /tmp/output/201701 and 2 in /tmp/output/201702 if currentDir is left empty", function () {
                photoSorter.copyFiles('../validate/testData/input', '/tmp/output/');
                expect(photoSorter.getListOfJpgFiles('/tmp/output/201701').length).toEqual(3);
                expect(photoSorter.getListOfJpgFiles('/tmp/output/201702').length).toEqual(2);
            }),
            it("should copy 2 jpg files in /tmp/output/201701 and 2 in /tmp/output/201702 if currentDir is set", function () {
                photoSorter.copyFiles('../validate/testData/input', '/tmp/output/', '../validate/testData/currentPhotos');
                expect(photoSorter.getListOfJpgFiles('/tmp/output/201701').length).toEqual(2);
                expect(photoSorter.getListOfJpgFiles('/tmp/output/201702').length).toEqual(2);
            });
    });
});
