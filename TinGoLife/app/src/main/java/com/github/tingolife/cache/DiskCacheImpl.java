package com.github.tingolife.cache;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.BaseDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.utils.IoUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with com.github.tingolife.cache
 * User:YangXiuFeng
 * Date:2016/4/21
 * Time:16:52
 */
public class DiskCacheImpl extends BaseDiskCache{
    public DiskCacheImpl(File cacheDir) {
        super(cacheDir);
    }

    public DiskCacheImpl(File cacheDir, File reserveCacheDir) {
        super(cacheDir, reserveCacheDir);
    }

    public DiskCacheImpl(File cacheDir, File reserveCacheDir, FileNameGenerator fileNameGenerator) {
        super(cacheDir, reserveCacheDir, fileNameGenerator);
    }

    @Override
    public File getDirectory() {
        return super.getDirectory();
    }

    @Override
    public File get(String imageUri) {
        return super.get(imageUri);
    }

    @Override
    public boolean save(String imageUri, InputStream imageStream, IoUtils.CopyListener listener) throws IOException {
        return super.save(imageUri, imageStream, listener);
    }

    @Override
    public boolean save(String imageUri, Bitmap bitmap) throws IOException {
        return super.save(imageUri, bitmap);
    }

    @Override
    public boolean remove(String imageUri) {
        return super.remove(imageUri);
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    protected File getFile(String imageUri) {
        return super.getFile(imageUri);
    }

    @Override
    public void setBufferSize(int bufferSize) {
        super.setBufferSize(bufferSize);
    }

    @Override
    public void setCompressFormat(Bitmap.CompressFormat compressFormat) {
        super.setCompressFormat(compressFormat);
    }

    @Override
    public void setCompressQuality(int compressQuality) {
        super.setCompressQuality(compressQuality);
    }
}
